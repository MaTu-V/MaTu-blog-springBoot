package com.mt.blog.controller;

import com.mt.blog.pojo.User;
import com.mt.blog.service.LoginRegisterService;
import com.mt.blog.service.UserService;
import com.mt.blog.utils.BlogJSONResult;
import com.mt.blog.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/loginRegister")
public class LoginRegisterController {
    @Autowired
    private LoginRegisterService loginRegisterService;
    @Autowired
    private BlogJSONResult result;

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return
     */
    @PostMapping("/register")
    public BlogJSONResult register(@RequestBody User user) {
        ServiceResult<String> serviceResult = loginRegisterService.registerUser(user);//注册
        return serviceResult.isSuccess() ? result.ok(serviceResult.getData()) : result.error(serviceResult.getCode(), serviceResult.getMsg());//返回数据
    }

    /**
     * 忘记密码
     *
     * @param user 用户信息
     * @return
     */
    @PostMapping("/forgetPwd")
    public BlogJSONResult forgetPwd(@RequestBody User user) {
        ServiceResult<String> serviceResult = loginRegisterService.forgetPwdUser(user);
        return serviceResult.isSuccess() ? result.ok(serviceResult.getData()) : result.error(serviceResult.getCode(), serviceResult.getMsg());//返回数据
    }

    /**
     * 用户登录
     * @param user 用户信息
     * @return
     */
    @PostMapping("/login")
    public BlogJSONResult login(@RequestBody User user) {
        ServiceResult<String> serviceResult = loginRegisterService.loginUser(user);
        return serviceResult.isSuccess() ? result.ok(serviceResult.getData()) : result.error(serviceResult.getCode(), serviceResult.getMsg());//返回数据
    }

    /**
     * 查询手机号是否被注册
     * @param map map集合保存用户信息（写法欠缺）
     * @return
     */
    @PostMapping("/selPhone")
    public BlogJSONResult selPhone(@RequestBody HashMap<String, String> map) {
        ServiceResult<Boolean> serviceResult = loginRegisterService.selUserByPhone(map.get("phone"));
        return result.ok(serviceResult.getCode(), serviceResult.getMsg());//返回数据
    }
}