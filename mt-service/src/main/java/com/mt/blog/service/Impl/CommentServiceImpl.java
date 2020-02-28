package com.mt.blog.service.Impl;

import com.auth0.jwt.JWT;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mt.blog.mapper.ArticleMapper;
import com.mt.blog.mapper.CommentMapper;
import com.mt.blog.pojo.Comment;
import com.mt.blog.pojo.User;
import com.mt.blog.service.AsyncService;
import com.mt.blog.service.CommentService;
import com.mt.blog.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private AESUtil aesUtil;

    /**
     * 查询当前文章的首条评论
     *
     * @param articleId
     * @param page
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<Map> selCommentReviewer(String articleId, Integer page, Integer pageSize) {
        // 判断文章id是否存在
        if (StringUtils.isEmpty(articleId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        Page<Object> pageData = PageHelper.startPage(page, pageSize);
        // 查询父评论信息
        LinkedList<Comment> comments = commentMapper.selCommentReviewer(articleId);
        Iterator<Comment> iterator = comments.iterator();
        // 查找子评论信息
        Map<String, Object> replyMap = new HashMap<>();
        while (iterator.hasNext()) {
            Comment next = iterator.next();
            next.getReviewerUser().setNickName(aesUtil.Decrypt(next.getReviewerUser().getNickName())); // 父评论名称解密
            LinkedList<Comment> commentsReply = new LinkedList<>();
            selCommentReply(articleId, next.getId(), commentsReply);
            replyMap.put(next.getId(), commentsReply); // 查询当前评论下的全部子评论
        }
        Map<String, Object> map = new HashMap<>();
        map.put("reviewer", PagedUtilHelper.genResultPagedData(comments, pageData));
        map.put("reply", replyMap); // 保存评论内容
        return ServiceResultHelper.genResultWithDataBaseSuccess(map);
    }
    /**
     * 查询当前网站下的评论
     *
     * @param page     当前页
     * @param pageSize 页面大小
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<Map> selWebSiteCommentReviewer(Integer page, Integer pageSize) {
        Page<Object> pageData = PageHelper.startPage(page, pageSize);
        // 查询父评论信息
        LinkedList<Comment> comments = commentMapper.selWebSiteCommentReviewer();
        Iterator<Comment> iterator = comments.iterator();
        // 查找子评论信息
        Map<String, Object> replyMap = new HashMap<>();
        while (iterator.hasNext()) {
            Comment next = iterator.next();
            next.getReviewerUser().setNickName(aesUtil.Decrypt(next.getReviewerUser().getNickName())); // 父评论名称解密
            LinkedList<Comment> commentsReply = new LinkedList<>();
            selCommentReply(null, next.getId(), commentsReply); // 父评论id
            replyMap.put(next.getId(), commentsReply); // 查询当前评论下的全部子评论
        }
        Map<String, Object> map = new HashMap<>();
        map.put("reviewer", PagedUtilHelper.genResultPagedData(comments, pageData));
        map.put("reply", replyMap); // 保存评论内容
        return ServiceResultHelper.genResultWithDataBaseSuccess(map);
    }
    /**
     * 查询子评论功能
     *
     * @param articleId 文章id
     * @param parentId  父评论id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void selCommentReply(String articleId, String parentId, LinkedList<Comment> commentsReply) {
        // 文章id不存在 查找网站子评论 否则查找对应文章子评论
        LinkedList<Comment> comments =
                StringUtils.isEmpty(articleId) ? commentMapper.selWebSiteCommentReply(parentId):commentMapper.selCommentReply(articleId,parentId);
        Iterator<Comment> iterator = comments.iterator();
        while (iterator.hasNext()) {
            Comment next = iterator.next(); // 获取评论信息
            next.getReplyUser().setNickName(aesUtil.Decrypt(next.getReplyUser().getNickName())); // 回复人解密
            next.getReviewerUser().setNickName(aesUtil.Decrypt(next.getReviewerUser().getNickName())); // 评论人解密
            commentsReply.add(next); // 将当前评论信息插入到列表中
            selCommentReply(articleId, next.getId(), commentsReply); // 递归遍历
        }
    }




    /**
     * 插入评论
     *
     * @param comment 评论信息
     * @param token   用户身份
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<Boolean> insComment(Comment comment, String token) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        // 从token中提取用户id信息失败
        if (StringUtils.isEmpty(userId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 设置评论人id
        comment.setId(UUID.randomUUID().toString());
        User user = new User();
        user.setId(userId);
        comment.setReviewerUser(user);
        comment.setCommentTime(TypeConvert.DateToString(new Date()));
        if (commentMapper.insComment(comment) > 0) {
            if(!StringUtils.isEmpty(comment.getArticleId())){
                asyncService.articleBlogStatus(3,comment.getArticleId()); // 统计文章评论数（异步）
            }
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
