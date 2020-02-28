package com.mt.blog.service.Impl;

import com.mt.blog.mapper.ArticleMapper;
import com.mt.blog.mapper.UserMapper;
import com.mt.blog.pojo.User;
import com.mt.blog.service.AsyncService;
import com.mt.blog.utils.AESUtil;
import com.mt.blog.utils.Constant;
import com.mt.blog.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AsyncServiceImpl implements AsyncService {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncService.class);
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AESUtil aesUtil;

    /**
     * 文章异步操作（点击数量统计、评论数量统计）
     *
     * @param blogStatus
     * @param articleId
     */
    @Async
    @Override
    public void articleBlogStatus(Integer blogStatus, String articleId) {
        articleMapper.updArticleInformationById(blogStatus, null, articleId, null); // 点击数量统计
    }

    /**
     * 用户发表文章异步操作（发表数量统计）
     *
     * @param blogStatus
     * @param userId
     */
    @Async
    @Override
    public void userArticleBlogStatus(Integer blogStatus, String userId) {
        userMapper.updUserInfoFollow(blogStatus, null, null, userId);
    }

    /**
     * 用户信息存入缓存中
     *
     * @param userId
     */
    @Async
    @Override
    public void userToRedis(String userId) {
        User user = userMapper.selUserInfoById(userId);
        user.setPassword("");
        user.setNickName(aesUtil.Decrypt(user.getNickName()));
        user.setPhone(aesUtil.Decrypt(user.getPhone()));
        redisUtil.set(Constant.RedisCode.USER_REDIS_CODE_KEY + userId,user,Constant.RedisCode.USER_REDIS_CODE_EXPIRE);
    }

    /**
     * 短信服务存入redis
     *
     * @param key key值
     * @param codeMap 短信信息集合
     * @param smsRedisCodeExpire 过期时间
     */
    @Async
    @Override
    public void sendSms(String key, Map<String, Object> codeMap, long smsRedisCodeExpire) {
        redisUtil.set(key, codeMap,smsRedisCodeExpire);
    }
}
