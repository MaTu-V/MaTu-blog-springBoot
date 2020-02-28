package com.mt.blog.service.Impl;

import com.auth0.jwt.JWT;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mt.blog.mapper.ArticleMapper;
import com.mt.blog.pojo.Article;
import com.mt.blog.pojo.User;
import com.mt.blog.service.ArticleService;
import com.mt.blog.service.AsyncService;
import com.mt.blog.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger LOG = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private AESUtil aesUtil;
    @Autowired
    private AsyncService asyncService;
    /**                  管理员针对文章信息操作                  **/


    /**                  查找所有文章信息操作                   **/
    /**
     * 根据id查找文章信息
     *
     * @param id 文章id
     * @return
     */

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<Article> selArticleById(String id) {
        // 判断是否有参数
        if (StringUtils.isEmpty(id)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        Article article = articleMapper.selArticleById(id); // 查找文章信息
        if(!StringUtils.isEmpty(article)){
            article.getUser().setNickName(aesUtil.Decrypt(article.getUser().getNickName())); // 解密用户名
            article.setLabels(articleMapper.selArticleLabel(article.getId())); // 查询文章对应的标签信息
            asyncService.articleBlogStatus(2,id); // 点击数量统计
            return ServiceResultHelper.genResultWithDataBaseSuccess(article); // 返回数据
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 查询指定类型用户文章
     *
     * @param categoryId 文章类型
     * @param page       当前页
     * @param pageSize   页面大小
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<PagedUtil> selArticle(Integer categoryId, Integer page, Integer pageSize) {
        // 设置查询的分页条件(多表连接设计外键查询可能会使得pageInfo失效)
        Page<Object> pageData = PageHelper.startPage(page, pageSize);
        // 查找个人文章信息
        LinkedList<Article> articles = articleMapper.selArticle(categoryId);
        Iterator<Article> iterator = articles.iterator();
        while (iterator.hasNext()) {
            Article next = iterator.next();
            next.getUser().setNickName(aesUtil.Decrypt(next.getUser().getNickName()));
            next.setLabels(articleMapper.selArticleLabel(next.getId()));
        }
        return ServiceResultHelper.genResultWithDataBaseSuccess(PagedUtilHelper.genResultPagedData(articles, pageData));
    }

    /**
     * 查询热门文章信息
     *
     * @param page     当前页 (默认 0 页）
     * @param pageSize 限制返回条数
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @CachePut(cacheNames = Constant.RedisCode.HOT_ARTICLE_REDIS_CODE_KEY)
    @Override
    public ServiceResult<PagedUtil> selHotArticle(Integer page, Integer pageSize) {
        // 设置查询的分页条件(多表连接设计外键查询可能会使得pageInfo失效)
        Page<Object> pageData = PageHelper.startPage(page, pageSize);
        // 查找个人文章信息
        LinkedList<Article> articles = articleMapper.selHotArticle();
        return ServiceResultHelper.genResultWithDataBaseSuccess(PagedUtilHelper.genResultPagedData(articles, pageData));
    }

    /**
     * 查询标签下文章信息
     *
     * @param labelId  标签id
     * @param page     当前页
     * @param pageSize 限制返回条数
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<PagedUtil> selLabelArticle(Integer labelId, Integer page, Integer pageSize) {
        if (StringUtils.isEmpty(labelId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 设置查询的分页条件(多表连接设计外键查询可能会使得pageInfo失效)
        Page<Object> pageData = PageHelper.startPage(page, pageSize);
        // 查找个人文章信息
        LinkedList<Article> articles = articleMapper.selLabelArticle(labelId);
        // todo:存入缓存中
        return ServiceResultHelper.genResultWithDataBaseSuccess(PagedUtilHelper.genResultPagedData(articles, pageData));
    }

    /**
     * 用户操作 (点赞、查看、评论、收藏)
     *
     * @param BlogStatus 状态(1、2、3、...)
     * @param articleId  文章id
     * @param token      用户身份
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<Boolean> updArticleInformationById(Integer BlogStatus, String articleId, String token) {
        // 判断
        if (StringUtils.isEmpty(BlogStatus) || StringUtils.isEmpty(articleId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        if (Math.abs(BlogStatus) > 3) {
            ServiceResult<String> serviceResult = getUserId(token);
            String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
            if (StringUtils.isEmpty(userId)) {
                return ServiceResultHelper.genResultWithParamFailed();
            }
            if (articleMapper.updArticleInformationById(BlogStatus, UUID.randomUUID().toString(), articleId, userId) > 0) {
                return ServiceResultHelper.genResultWithDataBaseSuccess();
            }
        } else {
            if (articleMapper.updArticleInformationById(BlogStatus, null, articleId, null) > 0) {
                return ServiceResultHelper.genResultWithDataBaseSuccess();
            }
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 文章是否已经被收藏
     *
     * @param articleId 文章id
     * @param token     用户身份
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<Boolean> isArticleCollectionExists(String articleId, String token) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(articleId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        if (articleMapper.isArticleCollectionExists(articleId, userId) > 0) {
            return ServiceResultHelper.genResultWithFailed("已收藏!"); // 返回数据已存在的信息
        }
        return ServiceResultHelper.genResultWithSuccess("未收藏!");
    }

    /**                  个人用户文章信息操作                   **/
    /**
     * 查询个人文章信息
     *
     * @param token    用户身份
     * @param page     当前页
     * @param pageSize 页面大小
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<PagedUtil> selArticleByUserId(String token, Integer page, Integer pageSize) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        // 从token中提取用户id信息失败
        if (StringUtils.isEmpty(userId)) {
            return ServiceResultHelper.genResultWithTokenFailed();
        }
        // 设置查询的分页条件(多表连接设计外键查询可能会使得pageInfo失效)
        Page<Object> pageData = PageHelper.startPage(page, pageSize);
        // 查找个人文章信息
        List<Article> articles = articleMapper.selArticleByUserId(userId);
        Iterator<Article> iterator = articles.iterator();
        while (iterator.hasNext()) {
            Article next = iterator.next();
            next.getUser().setNickName(aesUtil.Decrypt(next.getUser().getNickName()));
            next.setLabels(articleMapper.selArticleLabel(next.getId()));
        }
        // todo:存入缓存中
        return ServiceResultHelper.genResultWithDataBaseSuccess(PagedUtilHelper.genResultPagedData(articles, pageData));
    }

    /**
     * 添加个人文章信息
     *
     * @param article 文章数据
     * @param token   用户身份
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<String> insArticle(Article article, String token) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        // 从token中提取用户id信息失败
        if (StringUtils.isEmpty(userId)) {
            return ServiceResultHelper.genResultWithTokenFailed();
        }
        // 此时用户id需要保存到user中
        User user = new User();
        user.setId(userId);
        // 设置文章信息
        article.setId(UUID.randomUUID().toString());
        article.setUser(user);
        article.setEstablishTime(TypeConvert.DateToString(new Date()));
        asyncService.userArticleBlogStatus(2,userId); // 该用户发布数量统计(异步)
        if (articleMapper.insArticle(article) > 0) {
            // 将文章id作为数据返回
            return ServiceResultHelper.genResultWithDataBaseSuccess(article.getId());
        }
        return ServiceResultHelper.genResultWithFailed("发表失败!");
    }

    /**
     * 更新个人文章信息
     *
     * @param article 文章数据
     * @param token   用户身份
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<String> updArticle(Article article, String token) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        // 从token中提取用户id信息失败
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(article.getId())) {
            return ServiceResultHelper.genResultWithTokenFailed();
        }
        article.setEstablishTime(TypeConvert.DateToString(new Date()));
        if (articleMapper.updArticle(article) > 0) {
            // 将文章id作为数据返回
            return ServiceResultHelper.genResultWithDataBaseSuccess(article.getId());
        }
        return ServiceResultHelper.genResultWithFailed("保存失败!");
    }

    /**
     * 为当前个人文章添加标签信息
     *
     * @param articleId 文章id
     * @param labels    标签项
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<Boolean> insArticleLabel(String articleId, String[] labels) {
        if (StringUtils.isEmpty(articleId) || StringUtils.isEmpty(labels)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        if (articleMapper.insArticleLabel(articleId, labels) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 为当前个人文章更新标签信息
     *
     * @param articleId 文章id
     * @param labels    标签项
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<Boolean> updArticleLabel(String articleId, String[] labels) {
        if (StringUtils.isEmpty(articleId) || StringUtils.isEmpty(labels)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        articleMapper.delArticleLabel(articleId);// 删除原本分类标签
        if (articleMapper.insArticleLabel(articleId, labels) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 用户删除个人发表文章信息
     *
     * @param articleId 文章id
     * @param token     用户身份
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<String> delArticleByUserId(String articleId, String token) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        // 从token中提取用户id信息失败 或者 文章id不存在
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(articleId)) {
            return ServiceResultHelper.genResultWithTokenFailed();
        }
        if (articleMapper.delArticleByUserId(articleId, userId) > 0) {
            // 将文章id作为数据返回
            asyncService.userArticleBlogStatus(-2,userId); // 用户发布数量统计 （异步）
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 从token中提取用户id信息
     *
     * @param token 用户id
     * @return
     */
    private ServiceResult<String> getUserId(String token) {
        // 从请求体中取出用户信息
        // 获取用户token
        if (StringUtils.isEmpty(token)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        try {
            String userId = JWT.decode(token).getClaim("uId").asString(); // 从token中取出用户id
            return ServiceResultHelper.genResultWithTokenSuccess(userId);
        } catch (Exception e) {
            // e.printStackTrace();
            return ServiceResultHelper.genResultWithTokenFailed();
        }
    }
}
