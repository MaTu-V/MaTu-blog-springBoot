package com.mt.blog.service.Impl;

import com.mt.blog.service.MailService;
import com.mt.blog.utils.AESUtil;
import com.mt.blog.utils.ReadProteriesUtil;
import com.mt.blog.utils.ServiceResult;
import com.mt.blog.utils.ServiceResultHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;


@Service
public class MailServiceImpl implements MailService {
    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);
    @Autowired
    private ReadProteriesUtil readProteriesUtil;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AESUtil aesUtil;
    /**
     * 绑定邮箱业务
     * @param toEmail 发送邮箱地址
     * @param url 申请访问页面
     * @return
     */
    @Override
    public ServiceResult<Boolean> sendBindMail(String toEmail,String url) {

        if(StringUtils.isEmpty(toEmail) || StringUtils.isEmpty(url)){
            return ServiceResultHelper.genResultWithParamFailed();
        }
        String email = aesUtil.Decrypt(toEmail); // 邮箱地址解密
        String emailUrl = aesUtil.Decrypt(url); // 邮件中返回的页面地址
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom(readProteriesUtil.emailFrom);//设置发信人
            message.setTo(email);				//设置收信人
            message.setSubject("码途：邮箱绑定");	//设置主题
            message.setText( "<!DOCTYPE html><html><head><title>邮箱绑定</title><meta name=\"content-type\" content=\"text/html; charset=UTF-8\"></head><body>"
                    + "<p style=\"margin-bottom:30px;\">尊敬的 用户:</p>"
                    + "<p>您最近正在进行绑定邮箱操作，我们需要确认您得电子邮件地址，请单击此链接:</p>"
                    + "<a href=\""
                    + emailUrl + "?email=" + toEmail
                    +"\" class=\"color:blue;\">"
                    + emailUrl + "?email=" + toEmail
                    + "</a>"
                    + "<p>来自： 码途 </p>"
                    + "</body>"
                    + "</html>",true);  	//第二个参数true表示使用HTML语言
            javaMailSender.send(mimeMessage);
            return ServiceResultHelper.genResultWithSuccess("发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResultHelper.genResultWithFailed("发送失败!");
        }
    }

}
