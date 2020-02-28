package com.mt.blog.mapper;

import com.mt.blog.pojo.Article;
import com.mt.blog.pojo.Label;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;

@Repository
public interface ArticleMapper {
    /**                  管理员针对文章信息操作                  **/


    /**                  查找所有文章信息操作                   **/
    /**
     * 根据id查找文章信息
     *
     * @param id 文章id
     * @return
     */
    public Article selArticleById(String id);

    /**
     * 查找指定类型文章信息
     *
     * @return
     */
    public LinkedList<Article> selArticle(int categoryId);

    /**
     * 查询热门文章信息
     *
     * @return
     */
    public LinkedList<Article> selHotArticle();

    /**
     * 查询标签对应的文章信息
     *
     * @return
     */
    public LinkedList<Article> selLabelArticle(Integer labelId);

    /**
     * 用户操作 (点赞、查看、评论、收藏)
     *
     * @param BlogStatus 状态(1、2、3、...)
     * @param Id         收藏id
     * @param articleId  文章id
     * @param userId     操作人id
     * @return
     */
    public int updArticleInformationById(Integer BlogStatus, String Id, String articleId, String userId);

    /**
     * 用户是否已经收藏
     *
     * @param articleId 文章id
     * @param userId    用户id
     * @return
     */
    public int isArticleCollectionExists(String articleId, String userId);

    /**                  个人用户文章信息操作                   **/
    /**
     * 查询个人文章信息
     *
     * @param userId 用户个人id
     * @return
     */
    public LinkedList<Article> selArticleByUserId(String userId);

    /**
     * 添加个人文章信息
     *
     * @param article 待文章数据
     * @return
     */
    public int insArticle(Article article);

    /**
     * 更新文章信息
     *
     * @param article
     * @return
     */
    public int updArticle(Article article);

    /**
     * 为当前个人文章添加标签信息
     *
     * @param articleId 用户发表的文章id
     * @param labels    待添加的labels标签集合
     * @return
     */
    public int insArticleLabel(String articleId, String[] labels);

    /**
     * 为当前个人文章添加标签信息
     *
     * @param articleId 用户发表的文章id
     * @return
     */
    public int delArticleLabel(String articleId);

    /**
     * 查找当前个人文章标签信息
     *
     * @param articleId 文章id
     * @return
     */
    public LinkedList<Label> selArticleLabel(String articleId);

    /**
     * 用户删除个人发表文章信息
     *
     * @param articleId 文章id
     * @param userId    用户id
     * @return
     */
    public int delArticleByUserId(String articleId, String userId);


}
