package com.mt.blog.service.Impl;

import com.auth0.jwt.JWT;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mt.blog.mapper.ArticleMapper;
import com.mt.blog.mapper.UserMapper;
import com.mt.blog.pojo.User;
import com.mt.blog.service.AsyncService;
import com.mt.blog.service.UserService;
import com.mt.blog.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private AESUtil aesUtil;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 基于token验证获取用户信息
     *
     * @param token 用户token获取
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<User> getUserInfo(String token) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        // 获取用户id
        if (StringUtils.isEmpty(userId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 判断缓存中是否存在
        if (redisUtil.hasKey(Constant.RedisCode.USER_REDIS_CODE_KEY+ userId)){
            // 缓存中读取数据信息
            return  ServiceResultHelper.genResultWithDataBaseSuccess((User) redisUtil.get(Constant.RedisCode.USER_REDIS_CODE_KEY+ userId));
        }
        User userInfo = userMapper.selUserInfoById(userId);
        if (StringUtils.isEmpty(userInfo)) {
            return ServiceResultHelper.genResultWithFailed("读取信息失败");
        }
        // 数据已经加密（避免拦截）被截获 将 user.id 设置为null 当进行关联操作是将token做为交互条件
        // userInfo.setId("");
        userInfo.setNickName(aesUtil.Decrypt(userInfo.getNickName()));
        userInfo.setPhone(aesUtil.Decrypt(userInfo.getPhone()));
        asyncService.userToRedis(userId); // 异步存入缓存中
        return ServiceResultHelper.genResultWithDataBaseSuccess(userInfo);
    }

    /**
     * 修改基本信息
     *
     * @param user  用户信息
     * @param token 用户身份
     * @return
     */

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<Boolean> updUserInfo(User user, String token) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        // 获取用户id
        if (StringUtils.isEmpty(userId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        user.setId(userId);
        // 修改
        if(userMapper.updUserInfo(user)>0){
            asyncService.userToRedis(userId); // 异步存入缓存中
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 添加邮箱
     *
     * @param emailToken 邮箱帐户
     * @param token 用户身份
     * @return
     */

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<Boolean> updUserEmail(String emailToken,String token) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        String email = aesUtil.Decrypt(emailToken);
        // 获取用户id
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(email)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 修改
        if(userMapper.updUserEmail(email,userId)>0){
            asyncService.userToRedis(userId); // 异步存入缓存中
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 查询当前文章对应的用户个人信息
     *
     * @param userId 用户id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<User> selArticleUser(String userId) {
        userId = aesUtil.Decrypt(userId); // 解密
        if (StringUtils.isEmpty(userId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        User userInfo = userMapper.selUserInfoById(userId);
        if (StringUtils.isEmpty(userInfo)) {
            return ServiceResultHelper.genResultWithFailed("读取信息失败");
        }
        userInfo.setPassword(null);
        userInfo.setNickName(aesUtil.Decrypt(userInfo.getNickName()));
        return ServiceResultHelper.genResultWithDataBaseSuccess(userInfo);
    }

    /**
     * 关注
     *
     * @param BlogStatus 状态
     * @param followId   关注人id
     * @param token      用户身份
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<Boolean> followUser(Integer BlogStatus, String followId, String token) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        if (StringUtils.isEmpty(BlogStatus) || StringUtils.isEmpty(followId) || StringUtils.isEmpty(userId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        if (userMapper.updUserInfoFollow(BlogStatus, UUID.randomUUID().toString(), followId, userId) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 获取关注人或者粉丝信息
     *
     * @param BlogStatus 状态
     * @param userId     关注人id
     * @param page       当前页
     * @param pageSize   页面大小
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<Map> getUserFollowFan(Integer BlogStatus, String userId, Integer page, Integer pageSize) {
        userId = aesUtil.Decrypt(userId); // 解密
        if (StringUtils.isEmpty(BlogStatus) || StringUtils.isEmpty(userId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 设置查询的分页条件(多表连接设计外键查询可能会使得pageInfo失效)
        Page<Object> pageData = PageHelper.startPage(page, pageSize);
        // 状态判断
        LinkedList<User> users = BlogStatus == 0 ? userMapper.getUserFollow(userId) : userMapper.getUserFan(userId);
        Iterator<User> iterator = users.iterator();
        // 查找该用户对应的文章信息
        Map<String,Object> articleMap = new HashMap<>();
        while (iterator.hasNext()) {
            User next = iterator.next();
            next.setNickName(aesUtil.Decrypt(next.getNickName()));
            Page<Object> articleData = PageHelper.startPage(1,3); // 返回3条文章信息
            articleMap.put(userId,articleMapper.selArticleByUserId(userId)); // 数据信息
        }
        Map<String,Object> map = new HashMap<>();
        map.put("users",PagedUtilHelper.genResultPagedData(users, pageData));
        map.put("article",articleMap); // 保存三条文章内容
        return ServiceResultHelper.genResultWithDataBaseSuccess(map);
    }

    /**
     * 用户是否已经被关注
     *
     * @param followId 文章发表人id
     * @param token    用户身份
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<Boolean> isFollowExists(String followId, String token) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(followId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        if (userMapper.isFollowExists(followId, userId) > 0) {
            return ServiceResultHelper.genResultWithFailed("已关注!"); // 返回数据已存在的信息
        }
        return ServiceResultHelper.genResultWithSuccess("未关注!");
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