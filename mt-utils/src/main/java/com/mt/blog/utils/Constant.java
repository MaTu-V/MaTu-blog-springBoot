package com.mt.blog.utils;

import org.springframework.stereotype.Component;

@Component
public class Constant {
    /**
     * Service层
     */
    static class ErrorCode {
        /**
         * 无效参数
         */
        static Integer INVALID_PARAM_CODE = -101;
        static String INVALID_PARAM_MSG = "无效参数";
        /**
         * 登录/注册失效
         */
        static Integer INVALID_LOGIN_CODE = -102;
        static String INVALID_LOGIN_MSG = "用户登录/注册失败";
        /**
         * token失效
         */
        static Integer INVALID_TOKEN_CODE = -103;
        static String INVALID_TOKEN_MSG = "token生成/验证失败";
        /**
         * 没有权限
         */
        static Integer PERMISSION_DENIED_CODE = -104;
        static String PERMISSION_DENIED_MSG = "没有权限";
        /**
         * 数据库操作失败
         */
        static Integer DATABASE_OPERATION_ERROR_CODE = -105;
        static String DATABASE_OPERATION_ERROR_MSG = "数据库操作失败";

        /**
         * 数据库已存在该信息
         */
        static Integer ALREADY_EXISTS_CODE = -106;
        static String ALREADY_EXISTS_ERROR_MSG = "数据库已存在该信息";
        /**
         * 通用错误
         */
        static Integer COMMON_ERROR_CODE = -107;
        static String COMMON_ERROR_MSG = "服务器繁忙，请稍后再试";
        /**
         * 短信验证码发送失败
         */
        static Integer SEND_SMS_ERROR_CODE = -108;
        static String SEND_SMS_ERROR_MSG = "短信验证码，发送失败";
        /**
         * 手机号已被注册
         */
        static Integer PHONE_EXISTS_ERROR_CODE = -109;
        static String PHONE_EXISTS_ERROR_MSG = "手机号已被注册";
        /**
         * 服务器异常
         */
        static Integer SERVER_ERROR_CODE = 500;
        static String SERVER_ERROR_MSG = "服务器异常";

    }

    static class SuccessCode {
        /**
         * 登录/注册成功
         */
        public static Integer VALID_LOGIN_CODE = 102;
        public static String VALID_LOGIN_MSG = "登录/注册成功";
        /**
         * token有效
         */
        public static Integer INVALID_TOKEN_CODE = 103;
        public static String INVALID_TOKEN_MSG = "token生成/验证有效";
        /**
         * 具有当前权限
         */
        public static Integer PERMISSION_AGGREE_CODE = 104;
        public static String PERMISSION_AGGREE_MSG = "具有当前权限";

        /**
         * 数据库操作成功
         */
        public static Integer DATABASE_OPERATION_SUCCESS_CODE = 105;
        public static String DATABASE_OPERATION_SUCCESS_MSG = "数据库操作成功";
        /**
         * 通用执行成功
         */
        static Integer COMMON_SUCCESS_CODE = 107;
        static String COMMON_SUCCESS_MSG = "当前操作执行成功";
        /**
         * 短信验证码发送成功
         */
        static Integer SEND_SMS_SUCCESS_CODE = 108;
        static String SEND_SMS_SUCCESS_MSG = "短信验证码，发送成功";
        /**
         * 手机号未被注册
         */
        static Integer PHONE_EXISTS_SUCCESS_CODE = 109;
        static String PHONE_EXISTS_SUCCESS_MSG = "手机号未被注册";
        /**
         * 服务器正常
         */
        public static Integer SERVER_SUCCESS_CODE = 200;
        public static String SERVER_SUCCESS_MSG = "服务器运行正常";

    }

    /**
     * 缓存key值
     */
    public static class RedisCode {
        /**
         * 短信key
         */
        public static String SMS_REDIS_CODE_KEY = "SMS_REDIS_CODE_KEY";
        /**
         * 短信key生存时间
         */
        public static long SMS_REDIS_CODE_EXPIRE = 60;

        /**
         * 文章类型
         */
        public static final String CATEGORY_REDIS_CODE_KEY = "CATEGORY_REDIS_CODE_KEY";
        /**
         * 标签
         */
        public static final String LABEL_REDIS_CODE_KEY = "LABEL_REDIS_CODE_KEY";
        /**
         * 友链
         */
        public static final String LINK_REDIS_CODE_KEY = "LINK_REDIS_CODE_KEY";
        /**
         * 热门信息
         */
        public static final String HOT_ARTICLE_REDIS_CODE_KEY = "HOT_ARTICLE_REDIS_CODE_KEY";
        /**
         * 用户信息
         */
        public static final String USER_REDIS_CODE_KEY = "USER_REDIS_CODE_KEY:";
        public static final long USER_REDIS_CODE_EXPIRE = 60 * 60 * 24 * 15; // 保存15天
    }
}