package com.mt.blog.service;

import com.mt.blog.pojo.Comment;
import com.mt.blog.utils.PagedUtil;
import com.mt.blog.utils.ServiceResult;

import java.util.Map;

public interface CommentService {
    /**
     * 查询当前文章的首条评论
     *
     * @param page      当前页
     * @param pageSize  页面大小
     * @param articleId 文章id
     * @return
     */
    public ServiceResult<Map> selCommentReviewer(String articleId, Integer page, Integer pageSize);

    /**
     * 查询当前网站下的评论
     *
     * @param page     当前页
     * @param pageSize 页面大小
     * @return
     */
    public ServiceResult<Map> selWebSiteCommentReviewer(Integer page, Integer pageSize);

    /**
     * 插入评论
     *
     * @param comment 评论信息
     * @param token   用户身份
     * @return
     */
    public ServiceResult<Boolean> insComment(Comment comment, String token);
}
