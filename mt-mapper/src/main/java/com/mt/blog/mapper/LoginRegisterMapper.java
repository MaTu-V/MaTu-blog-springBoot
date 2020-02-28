package com.mt.blog.mapper;

import com.mt.blog.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRegisterMapper {

    // 用户注册
    public int registerUser(User user);

    // 根据手机号查找是否存在用户信息
    public User selUserByPhone(String phone);

    // 根据手机号修改密码
    public int forgetPwdUserByPhone(User user);

    // 用户登录
    public User loginUser(User user);

}
