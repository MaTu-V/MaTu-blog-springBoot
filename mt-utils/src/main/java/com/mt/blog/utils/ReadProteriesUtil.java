package com.mt.blog.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReadProteriesUtil {
    // 签名
    public final String signName = "码图网";
    //替换成你的AK
    @Value("${aliyun.com.accessKeyId}")
    public String accessKeyId;
    @Value("${aliyun.com.accessKeySecret}")
    public String accessKeySecret;
    @Value("${aliyun.com.loginAndRegisterTemplate}")
    public String loginAndRegisterTemplate;
    @Value("${aliyun.com.reviseTemplate}")
    public String reviseTemplate;
    @Value("${blog.com.tokenSecret}")
    public String tokenSecret;
    @Value("${blog.com.aesSecret}")
    public String aesSecret;
    // 图片上传地址
    @Value("${blog.file.space}")
    public String FILE_SPACE;
    // 邮箱发送地址
    @Value("${spring.mail.username}")
    public String emailFrom;
}
