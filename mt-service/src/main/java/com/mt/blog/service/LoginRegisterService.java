package com.mt.blog.service;

import com.mt.blog.pojo.User;
import com.mt.blog.utils.ServiceResult;

public interface LoginRegisterService {
    /**
     * 用户注册添加
     *
     * @param user 用户数据
     * @return
     */
    public ServiceResult<String> registerUser(User user);

    /**
     * 根据手机号判断用户信息是否被注册
     *
     * @param phone 用户手机号
     * @return
     */
    public ServiceResult<Boolean> selUserByPhone(String phone);

    /**
     * 更新用户密码
     *
     * @param user 用户数据
     * @return
     */
    public ServiceResult<String> forgetPwdUser(User user);

    /**
     * 用户登录
     *
     * @param user 用户数据
     * @return
     */
    public ServiceResult<String> loginUser(User user);
}
