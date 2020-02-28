package com.mt.blog.service;

import com.mt.blog.utils.ServiceResult;

public interface SmsService {

    /**
     * 发送短信服务
     *
     * @param phone    手机号
     * @param template 模板
     * @return
     */
    public ServiceResult<Boolean> sendSms(String phone,String template);

    /**
     * 判断验证码是否正确
     *
     * @param phone        手机号
     * @param identifyCode 输入验证码
     * @return
     */
    public ServiceResult<Boolean> checkSms(String phone, String identifyCode);
}
