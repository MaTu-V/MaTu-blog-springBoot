package com.mt.blog.service;

import com.mt.blog.pojo.Article;
import com.mt.blog.utils.PagedUtil;
import com.mt.blog.utils.ServiceResult;


public interface ArticleService {
    /**                  管理员针对文章信息操作                  **/

    /**                  查找所有文章信息操作                   **/
    /**
     * 根据id查找文章信息
     *
     * @param id 文章id
     * @return
     */
    public ServiceResult<Article> selArticleById(String id);

    /**
     * 查询指定类型用户文章
     *
     * @param categoryId 文章类型
     * @param page       当前页
     * @param pageSize   页面大小
     * @return
     */
    public ServiceResult<PagedUtil> selArticle(Integer categoryId, Integer page, Integer pageSize);

    /**
     * 查询热门文章信息
     *
     * @param page     当前页
     * @param pageSize 限制返回条数
     * @return
     */
    public ServiceResult<PagedUtil> selHotArticle(Integer page, Integer pageSize);
    /**
     * 查询标签下文章信息
     *
     * @param labelId  标签id
     * @param page     当前页
     * @param pageSize 限制返回条数
     * @return
     */
    public ServiceResult<PagedUtil> selLabelArticle(Integer labelId,Integer page, Integer pageSize);
    /**
     * 用户操作 (点赞、查看、评论、收藏)
     *
     * @param BlogStatus 状态(1、2、3、...)
     * @param articleId  文章id
     * @param token      用户身份
     * @return
     */
    public ServiceResult<Boolean> updArticleInformationById(Integer BlogStatus, String articleId, String token);

    /**
     * 文章是否已经被收藏
     *
     * @param articleId 文章id
     * @param token     用户身份
     * @return
     */
    public ServiceResult<Boolean> isArticleCollectionExists(String articleId, String token);

    /**                  个人用户文章信息操作                   **/
    /**
     * 查询用户个人文章信息
     *
     * @param token    用户身份
     * @param page     当前页
     * @param pageSize 页面大小
     * @return
     */
    public ServiceResult<PagedUtil> selArticleByUserId(String token, Integer page, Integer pageSize);

    /**
     * 用户个人发表文章信息
     *
     * @param article 文章数据
     * @param token   用户身份
     * @return 返回为文章id
     */
    public ServiceResult<String> insArticle(Article article, String token);

    /**
     * 用户个人发表文章信息
     *
     * @param article 文章数据
     * @param token   用户身份
     * @return 返回为文章id
     */
    public ServiceResult<String> updArticle(Article article, String token);

    /**
     * 文章添加标签
     *
     * @param articleId 文章id
     * @param labels    标签项
     * @return
     */
    public ServiceResult<Boolean> insArticleLabel(String articleId, String[] labels);

    /**
     * 文章添加标签
     *
     * @param articleId 文章id
     * @param labels    标签项
     * @return
     */
    public ServiceResult<Boolean> updArticleLabel(String articleId, String[] labels);

    /**
     * 用户删除个人发表文章信息
     *
     * @param articleId 文章id
     * @param token     用户身份
     * @return
     */
    public ServiceResult<String> delArticleByUserId(String articleId, String token);
}
