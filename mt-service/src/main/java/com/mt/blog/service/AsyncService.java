package com.mt.blog.service;

import java.util.Map;

public interface AsyncService {


    /**
     * 文章异步操作（点击数量统计、评论数量统计）
     *
     * @param blogStatus
     * @param articleId
     */
    public void articleBlogStatus(Integer blogStatus, String articleId);

    /**
     * 用户发表文章异步操作（发表数量统计）
     *
     * @param blogStatus
     * @param userId
     */
    public void userArticleBlogStatus(Integer blogStatus, String userId);

    /**
     * 用户信息存入缓存中
     */
    public void userToRedis(String userId);

    /**
     * 短信服务存入redis
     */
    public void sendSms(String key, Map<String, Object> codeMap, long smsRedisCodeExpire);
}
