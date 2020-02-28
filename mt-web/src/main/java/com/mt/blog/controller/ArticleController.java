package com.mt.blog.controller;

import com.mt.blog.pojo.Article;
import com.mt.blog.service.ArticleService;
import com.mt.blog.utils.BlogJSONResult;
import com.mt.blog.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private BlogJSONResult result;

    /**
     * 根据id查找对应文章信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getArticleById")
    public BlogJSONResult getArticleById(@RequestParam String id) {
        ServiceResult<Article> serviceResult = articleService.selArticleById(id);
        return serviceResult.isSuccess()?result.ok(serviceResult.getData()):result.error(serviceResult.getCode(),serviceResult.getMsg()); // 获取数据
    }

    /**
     * 获取对应文章分类信息
     *
     * @param categoryId 文章类型
     * @param page       当前页
     * @param pageSize   页面大小
     * @return
     */
    @GetMapping("/getArticle")
    public BlogJSONResult getArticle(@RequestParam Integer categoryId,
                                     @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "8") Integer pageSize) {
        return result.ok(articleService.selArticle(categoryId, page, pageSize)); // 获取数据
    }

    /**
     * 获取热门文章数据
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/getHotArticle")
    public BlogJSONResult getHotArticle(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer pageSize) {
        return result.ok(articleService.selHotArticle(page, pageSize)); // 获取数据
    }
    /**
     * 获取标签下文章信息
     *
     * @param labelId 标签id
     * @param page 当前页
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/getLabelArticle")
    public BlogJSONResult getHotArticle(@RequestParam Integer labelId,@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "8") Integer pageSize) {
        return result.ok(articleService.selLabelArticle(labelId,page, pageSize)); // 获取数据
    }
    /**
     * 文章操作更新(点赞、阅读、评论、收藏)
     * @param BlogStatus 操作状态
     * @param articleId 文章id
     * @return
     */
    @GetMapping("/updArticleInformation")
    public BlogJSONResult updArticleInformation(@RequestParam("BlogStatus") Integer BlogStatus,@RequestParam("articleId") String articleId) {
        String token = httpServletRequest.getHeader("public-token"); // 获取用户身份 token
        ServiceResult<Boolean> serviceResult = articleService.updArticleInformationById(BlogStatus,articleId,token); // 插入
        return serviceResult.isSuccess() ? result.ok(serviceResult.getData()) : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回状态信息
    }

    /**
     * 查询文章是否被收藏
     * @param articleId 文章id
     * @return
     */
    @GetMapping("/isArticleCollectionExists")
    public BlogJSONResult isArticleCollectionExists(@RequestParam("articleId") String articleId) {
        String token = httpServletRequest.getHeader("public-token"); // 获取用户身份 token
        ServiceResult<Boolean> serviceResult = articleService.isArticleCollectionExists(articleId,token); // 插入
        return serviceResult.isSuccess() ? result.ok(serviceResult.getCode(),serviceResult.getMsg()) : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回状态信息
    }
    /**
     * 根据用户id获取个人文章信息
     *
     * @param page     当前页
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/getArticleByUserId")
    public BlogJSONResult getArticleByUserId(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "8") Integer pageSize) {
        String token = httpServletRequest.getHeader("public-token");  // 获取token
        return result.ok(articleService.selArticleByUserId(token, page, pageSize)); // 返回数据
    }

    /**
     * 插入文章信息
     *
     * @param article 文章内容
     * @return
     */
    @PostMapping("/insArticle")
    public BlogJSONResult insArticle(@RequestBody Article article) {
        String token = httpServletRequest.getHeader("public-token"); // 获取用户身份 token
        ServiceResult<String> serviceResult = articleService.insArticle(article, token); // 插入
        return serviceResult.isSuccess() ? result.ok(serviceResult.getData()) : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回状态信息
    }

    /**
     * 更新文章信息
     *
     * @param article 文章内容
     * @return
     */
    @PostMapping("/updArticle")
    public BlogJSONResult updArticle(@RequestBody Article article) {
        String token = httpServletRequest.getHeader("public-token"); // 获取用户身份 token
        ServiceResult<String> serviceResult = articleService.updArticle(article, token); // 插入
        return serviceResult.isSuccess() ? result.ok(serviceResult.getData()) : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回状态信息
    }

    /**
     * 插入文章标签
     *
     * @param articleId 文章id
     * @param labels    文章标签
     * @return
     */
    @GetMapping("/insArticleLabel")
    public BlogJSONResult insArticleLabel(String articleId, String[] labels) {
        ServiceResult<Boolean> serviceResult = articleService.insArticleLabel(articleId, labels); // 插入
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回信息
    }

    /**
     * 更新文章标签
     *
     * @param articleId 文章id
     * @param labels    文章标签
     * @return
     */
    @GetMapping("/updArticleLabel")
    public BlogJSONResult updArticleLabel(String articleId, String[] labels) {
        ServiceResult<Boolean> serviceResult = articleService.updArticleLabel(articleId, labels); // 插入
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回信息
    }

    /**
     * 用户删除个人文章信息
     *
     * @param articleId 文章id
     * @return
     */
    @GetMapping("/deleteArticleByUserId")
    public BlogJSONResult delArticleByUserId(@RequestParam("articleId") String articleId) {
        String token = httpServletRequest.getHeader("public-token"); // 获取用户身份 token
        ServiceResult<String> serviceResult = articleService.delArticleByUserId(articleId, token); // 插入
        return serviceResult.isSuccess() ? result.ok(serviceResult.getData()) : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回状态信息
    }
}
