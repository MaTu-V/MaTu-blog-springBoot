package com.mt.blog.service;

import com.mt.blog.pojo.User;
import com.mt.blog.utils.PagedUtil;
import com.mt.blog.utils.ServiceResult;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.Map;

public interface UserService {

    /**
     * 基于token验证获取用户信息
     *
     * @param token 用户token
     * @return
     */
    public ServiceResult<User> getUserInfo(String token);

    /**
     * 修改基本信息
     *
     * @param user  用户信息
     * @param token 用户身份
     * @return
     */
    public ServiceResult<Boolean> updUserInfo(User user, String token);

    /**
     * 添加邮箱
     * @param emailToken 邮箱帐户
     * @param token 用户身份
     * @return
     */
    public ServiceResult<Boolean> updUserEmail(String emailToken,String token);
    /**
     * 查询用户个人介绍
     *
     * @param userId
     * @return
     */
    public ServiceResult<User> selArticleUser(String userId);

    /**
     * 关注
     *
     * @param BlogStatus 状态
     * @param followId   关注人id
     * @param token      用户身份
     * @return
     */
    public ServiceResult<Boolean> followUser(Integer BlogStatus, String followId, String token);

    /**
     * 获取关注人或者粉丝信息
     *
     * @param BlogStatus 状态
     * @param userId     关注人id
     * @param page       当前页
     * @param pageSize   页面大小
     * @return
     */
    public ServiceResult<Map> getUserFollowFan(Integer BlogStatus, String userId, Integer page, Integer pageSize);

    /**
     * 查询是否被关注
     *
     * @param followId 文章发表人id
     * @param token    当前用户身份
     * @return
     */
    public ServiceResult<Boolean> isFollowExists(String followId, String token);


}