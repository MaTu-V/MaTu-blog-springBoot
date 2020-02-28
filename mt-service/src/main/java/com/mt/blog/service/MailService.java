package com.mt.blog.service;

import com.mt.blog.utils.ServiceResult;

public interface MailService {

    /**
     * 绑定邮箱业务
     *
     * @param toEmail 发送邮箱地址
     * @param url 申请访问页面
     * @return
     */
    public ServiceResult<Boolean> sendBindMail(String toEmail,String url);
}
