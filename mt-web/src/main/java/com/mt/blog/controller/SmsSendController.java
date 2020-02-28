package com.mt.blog.controller;

import com.mt.blog.service.SmsService;
import com.mt.blog.utils.BlogJSONResult;
import com.mt.blog.utils.ReadProteriesUtil;
import com.mt.blog.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/sms")
public class SmsSendController {
    @Autowired
    private SmsService smsService;
    @Autowired
    private ReadProteriesUtil readProteriesUtil;
    @Autowired
    private BlogJSONResult result;

    /**
     * 发送登录短信验证码信息
     * @param Sms 短信信息
     * @return
     */
    @PostMapping("/sendLoginSms")
    public BlogJSONResult sendLoginSms(@RequestBody HashMap<String, String> Sms) {
        ServiceResult<Boolean> serviceResult = smsService.sendSms(Sms.get("phone"),readProteriesUtil.loginAndRegisterTemplate); // 发送登录短信服务
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg());// 返回数据
    }

    /**
     * 发送忘记密码短信验证码信息
     * @param Sms 短信信息
     * @return
     */
    @PostMapping("/sendForgetSms")
    public BlogJSONResult sendForgetSms(@RequestBody HashMap<String, String> Sms) {
        ServiceResult<Boolean> serviceResult = smsService.sendSms(Sms.get("phone"),readProteriesUtil.reviseTemplate); //忘记密码短信发送
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回数据
    }

    /**
     * 检测短信验证码是否正确
     * @param Sms 短信信息
     * @return
     */
    @PostMapping("/checkSms")
    public BlogJSONResult checkSms(@RequestBody HashMap<String, String> Sms) {
        ServiceResult<Boolean> serviceResult = smsService.checkSms(Sms.get("phone"), Sms.get("identifyCode")); // 短信验证
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回数据
    }
}