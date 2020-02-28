package com.mt.blog.service.Impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.mt.blog.service.AsyncService;
import com.mt.blog.service.SmsService;
import com.mt.blog.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SmsServiceImpl implements SmsService {
    private static final Logger LOG = LoggerFactory.getLogger(SmsServiceImpl.class);
    //短信API产品名称（短信产品名固定，无需修改）
    private static final String product = "Dysmsapi";
    //短信API产品域名（接口地址固定，无需修改）
    private static final String domain = "dysmsapi.aliyuncs.com";
    @Autowired
    private ReadProteriesUtil readProteriesUtil;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    AESUtil aesUtil;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 发送登录/注册/修改密码短信服务
     *
     * @param phone    手机号
     * @param template 模板
     * @return
     */

    @Override
    public ServiceResult<Boolean> sendSms(String phone, String template) {
        String phoneInfo;
        // 参数不正确
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(template) || StringUtils.isEmpty(phoneInfo = aesUtil.Decrypt(phone))) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        //设定redis-key
        String key = Constant.RedisCode.SMS_REDIS_CODE_KEY + ": " + phone;
        if (redisUtil.hasKey(key)) {
            return ServiceResultHelper.genResultWithFailed("请勿多次发送");
        }
        // 生成验证码
        String code = getMsgCode();
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("phone", phone);
        codeMap.put("code", code);
        // 发送短信服务(使用解密后的手机号)
        SendSmsResponse smsResponse = smsProvide(phoneInfo, template, code);
        if (smsResponse != null && smsResponse.getCode().equals("OK") && !StringUtils.isEmpty(smsResponse.getCode())) {
            asyncService.sendSms(key,codeMap,Constant.RedisCode.SMS_REDIS_CODE_EXPIRE); // 异步存入缓存中
            return ServiceResultHelper.genResultWithSmsSuccess();
        }
        return ServiceResultHelper.genResultWithSmsFailed();//发送失败
    }

    /**
     * 判断验证码是否正确
     *
     * @param phone        手机号
     * @param identifyCode 用户输入验证码
     * @return
     */
    @Override
    public ServiceResult<Boolean> checkSms(String phone, String identifyCode) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(identifyCode)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 缓存中读取验证码 和用户联系方式
        String key = Constant.RedisCode.SMS_REDIS_CODE_KEY + ": " + phone; // key
        if (!redisUtil.hasKey(key)) {
            return ServiceResultHelper.genResultWithFailed("验证码已过期");
        }
        Map<String, Object> map = (Map<String, Object>) redisUtil.get(key);
        if (identifyCode.equals(map.get("code")) && phone.equals(map.get("phone"))) {
            //解决:直接删除当前缓存中数据(或者等待自动回收)
            return ServiceResultHelper.genResultWithSuccess("验证码核对成功");
        }
        return ServiceResultHelper.genResultWithFailed("验证码输入有误");
    }

    /**
     * @param phone        手机号
     * @param templateCode 模板
     * @param code         验证码
     * @return
     */
    private SendSmsResponse smsProvide(String phone, String templateCode, String code) {
        try {//设置超时时间-可自行调整
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            // 初始化acsClient
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", readProteriesUtil.accessKeyId, readProteriesUtil.accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(readProteriesUtil.signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            request.setTemplateParam("{\"code\":\"" + code + "\"}");
            return acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            return null;
        }
    }

    /**
     * 生成随机的6位数，短信验证码
     *
     * @return
     */
    private static String getMsgCode() {
        int n = 6;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < n; i++) {
            code.append(Integer.valueOf(ran.nextInt(10)).toString());
        }
        return code.toString();
    }
}