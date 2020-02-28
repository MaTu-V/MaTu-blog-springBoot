package com.mt.blog.service.Impl;

import com.mt.blog.mapper.LoginRegisterMapper;
import com.mt.blog.pojo.User;
import com.mt.blog.service.AsyncService;
import com.mt.blog.service.LoginRegisterService;
import com.mt.blog.service.TokenService;
import com.mt.blog.utils.AESUtil;
import com.mt.blog.utils.ServiceResult;
import com.mt.blog.utils.ServiceResultHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class LoginRegisterServiceImpl implements LoginRegisterService {
    private static final Logger LOG = LoggerFactory.getLogger(LoginRegisterServiceImpl.class);
    @Autowired
    private LoginRegisterMapper loginRegisterMapper;
    @Autowired
    private AESUtil aesUtil;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AsyncService asyncService;

    /**
     * 用户注册添加
     *
     * @param user
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<String> registerUser(User user) {
        String userPhone;
        // 用户信息不为空
        if (StringUtils.isEmpty(user) || StringUtils.isEmpty(aesUtil.Decrypt(user.getPassword())) || StringUtils.isEmpty(userPhone = aesUtil.Decrypt(user.getPhone()))) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 查找当前用户手机号是否存在
        User userInfo = loginRegisterMapper.selUserByPhone(userPhone);
        // 判断用户信息
        if (!StringUtils.isEmpty(userInfo)) {
            return ServiceResultHelper.genResultWithFailed("该手机号已存在");
        }
        // 补全用户信息完善
        user.setId(UUID.randomUUID().toString());
        user.setNickName(user.getPhone());
        // 将用户信息插入
        if (loginRegisterMapper.registerUser(user) > 0) {
            asyncService.userToRedis(user.getId()); // 用户信息放入缓存中
            return tokenService.getToken(user.getId());// 服务端返回包含用户id 的 token
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 根据手机号判断用户信息是否被注册
     *
     * @param phone
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<Boolean> selUserByPhone(String phone) {
        // 判断手机号为空或者手机号解密失败
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(aesUtil.Decrypt(phone))) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 对手机号解密  读取用户信息
        User userInfo = loginRegisterMapper.selUserByPhone(phone);
        // 判断用户信息是否存在
        if (!StringUtils.isEmpty(userInfo)) {
            return ServiceResultHelper.genResultWithPhoneFailed();
        }
        return ServiceResultHelper.genResultWithPhoneSuccess();
    }

    /**
     * 更新用户密码
     *
     * @param user
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<String> forgetPwdUser(User user) {
        // 用户信息不为空
        if (StringUtils.isEmpty(user) || StringUtils.isEmpty(aesUtil.Decrypt(user.getPassword())) || StringUtils.isEmpty(aesUtil.Decrypt(user.getPhone()))) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 更新用户信息密码
        if (loginRegisterMapper.forgetPwdUserByPhone(user) > 0) {
            // 获取更新后用户信息
            User userInfo = loginRegisterMapper.selUserByPhone(user.getPhone());
            asyncService.userToRedis(userInfo.getId()); // 用户信息放入缓存中
            return tokenService.getToken(userInfo.getId());   // 用户信息基于token验证返回给客户端
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceResult<String> loginUser(User user) {
        // 用户信息不为空
        if (StringUtils.isEmpty(user) ||
                StringUtils.isEmpty(aesUtil.Decrypt(user.getPhone())) ||
                StringUtils.isEmpty(aesUtil.Decrypt(user.getPassword()))) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 查询用户信息
        User userInfo = loginRegisterMapper.loginUser(user);
        if (!StringUtils.isEmpty(userInfo)) {
            asyncService.userToRedis(userInfo.getId()); // 用户信息放入缓存中
            return tokenService.getToken(userInfo.getId()); // 用户信息基于token验证返回给客户端
        }
        // 用户信息不存在
        return ServiceResultHelper.genResultWithFailed("账号或密码有误!");
    }
}
