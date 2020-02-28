package com.mt.blog.mapper;

import com.mt.blog.pojo.Comment;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface CommentMapper {

    /**
     * 查询当前文章的首条评论
     *
     * @param articleId 文章id
     * @return
     */
    public LinkedList<Comment> selCommentReviewer(String articleId);

    /**
     * 查询有关网站下首条评论
     *
     * @return
     */
    public LinkedList<Comment> selWebSiteCommentReviewer();

    /**
     * 查询当前网站下父评论的子评论
     *
     * @param parentId  当前评论的id
     * @return
     */
    public LinkedList<Comment> selWebSiteCommentReply(String parentId);

    /**
     * 查询当前评论下的子评论
     *
     * @param articleId 文章id
     * @param parentId  当前评论的id
     * @return
     */
    public LinkedList<Comment> selCommentReply(String articleId, String parentId);

    /**
     * 插入评论
     *
     * @param comment 评论信息
     * @return
     */
    int insComment(Comment comment);
}
