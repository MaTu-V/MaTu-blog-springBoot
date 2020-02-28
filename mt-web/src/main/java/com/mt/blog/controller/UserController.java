package com.mt.blog.controller;

import com.mt.blog.pojo.User;
import com.mt.blog.service.UserService;
import com.mt.blog.utils.BlogJSONResult;
import com.mt.blog.utils.PagedUtil;
import com.mt.blog.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogJSONResult result;

    /**
     * 用户获取个人信息
     *
     * @return
     */
    @PostMapping("/getUserInfo")
    public BlogJSONResult getUserInfo() {
        ServiceResult<User> serviceResult = userService.getUserInfo(httpServletRequest.getHeader("public-token")); // 获取用户token
        return serviceResult.isSuccess() ? result.ok(serviceResult.getData()) : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回数据
    }

    @PostMapping("/updUserInfo")
    public BlogJSONResult updUserInfo(@RequestBody User user){
        String token = httpServletRequest.getHeader("public-token");
        ServiceResult<Boolean> serviceResult = userService.updUserInfo(user,token);
        return serviceResult.isSuccess()?result.ok():result.error(serviceResult.getCode(),serviceResult.getMsg());
    }

    @GetMapping("/updUserEmail/{emailToken}")
    public BlogJSONResult updUserEmail(@PathVariable("emailToken") String emailToken){
        String token = httpServletRequest.getHeader("public-token");
        ServiceResult<Boolean> serviceResult = userService.updUserEmail(emailToken,token);
        return serviceResult.isSuccess()? result.ok():result.error(serviceResult.getCode(),serviceResult.getMsg());
    }
    /**
     * 获取当前文章的用户个人信息
     *
     * @return
     */
    @GetMapping("/getArticleUser/{userId}")
    public BlogJSONResult getArticleUser(@PathVariable("userId") String userId) {
        ServiceResult<User> serviceResult = userService.selArticleUser(userId); // 获取用户信息
        return serviceResult.isSuccess() ? result.ok(serviceResult.getData()) : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回数据
    }

    /**
     * 关注当前用户
     *
     * @return
     */
    @GetMapping("/followUser")
    public BlogJSONResult followUser(Integer BlogStatus, String followId) {
        String token = httpServletRequest.getHeader("public-token"); // 获取用户token
        ServiceResult<Boolean> serviceResult = userService.followUser(BlogStatus, followId, token); // 关注
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回数据
    }

    /**
     * 获取关注人或者粉丝信息
     *
     * @return
     */
    @GetMapping("/getUserFollowFan")
    public BlogJSONResult getUserFollowFan(@RequestParam("BlogStatus") Integer BlogStatus,@RequestParam("userId") String userId, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        ServiceResult<Map> serviceResult = userService.getUserFollowFan(BlogStatus, userId, page, pageSize); // 获取关注、粉丝信息
        return serviceResult.isSuccess() ? result.ok(serviceResult.getData()) : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回数据
    }

    /**
     * 查询是否被关注
     *
     * @param followId 文章发表人id
     * @return
     */
    @GetMapping("/isFollowExists")
    public BlogJSONResult isFollowExists(@RequestParam("followId") String followId) {
        String token = httpServletRequest.getHeader("public-token"); // 获取用户身份 token
        ServiceResult<Boolean> serviceResult = userService.isFollowExists(followId, token); // 插入
        return serviceResult.isSuccess() ? result.ok(serviceResult.getCode(), serviceResult.getMsg()) : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回状态信息
    }
}