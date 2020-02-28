package com.mt.blog.utils;

import org.springframework.stereotype.Component;

/**
 * 快速构建
 */
@Component
public class ServiceResultHelper {

    public ServiceResultHelper() {
    }

    /**
     * 调用创建
     *
     * @param code    service服务状态码
     * @param msg     service服务状态信息
     * @param data    service服务状态数据集
     * @param success service服务响应成功
     * @param <T>     对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResult(int code, String msg, T data, boolean success) {
        ServiceResult<T> result = ServiceResult.create();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        result.setSuccess(success);
        return result;
    }

    public static <T> ServiceResult<T> genResult(int code, String msg, boolean success) {
        ServiceResult<T> result = ServiceResult.create();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(success);
        return result;
    }
    /**------------------------------------响应成功---------------------------------------------**/
    /**
     * 业务层响应成功 （自定义返回信息）
     *
     * @param msg 当前业务响应成功数据
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithSuccess(String msg) {
        return genResult(Constant.SuccessCode.COMMON_SUCCESS_CODE, msg, null, true);
    }

    /**
     * 业务层服务响应成功（携带数据）
     *
     * @param data 当前业务响应成功数据
     * @param <T>  对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithSuccess(T data) {
        return genResult(Constant.SuccessCode.COMMON_SUCCESS_CODE, Constant.SuccessCode.COMMON_SUCCESS_MSG, data, true);
    }

    /**
     * 数据库服务响应成功（增/删/改/差）
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithDataBaseSuccess() {
        return genResult(Constant.SuccessCode.DATABASE_OPERATION_SUCCESS_CODE, Constant.SuccessCode.DATABASE_OPERATION_SUCCESS_MSG, null, true);
    }

    /**
     * 数据库服务响应成功（增/删/改/差）
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithDataBaseSuccess(T data) {
        return genResult(Constant.SuccessCode.DATABASE_OPERATION_SUCCESS_CODE, Constant.SuccessCode.DATABASE_OPERATION_SUCCESS_MSG, data, true);
    }

    /**
     * 登录/注册成功
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithLoginSuccess() {
        return genResult(Constant.SuccessCode.VALID_LOGIN_CODE, Constant.SuccessCode.VALID_LOGIN_MSG, null, true);
    }

    /**
     * token验证成功
     * @param data 返回数据
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithTokenSuccess(T data) {
        return genResult(Constant.SuccessCode.INVALID_TOKEN_CODE, Constant.SuccessCode.INVALID_TOKEN_MSG, data, true);
    }

    /**
     * 短信服务发送成功
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithSmsSuccess() {
        return genResult(Constant.SuccessCode.SEND_SMS_SUCCESS_CODE, Constant.SuccessCode.SEND_SMS_SUCCESS_MSG, null, true);
    }

    /**
     * 手机号未被注册
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithPhoneSuccess() {
        return genResult(Constant.SuccessCode.PHONE_EXISTS_SUCCESS_CODE, Constant.SuccessCode.PHONE_EXISTS_SUCCESS_MSG, null, true);
    }
    /**------------------------------------响应失败---------------------------------------------**/
    /**
     * service响应自定义错误
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithFailed(String msg) {
        return genResult(Constant.ErrorCode.COMMON_ERROR_CODE, msg, null, false);
    }

    /**
     * service服务中参数有误
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithParamFailed() {
        return genResult(Constant.ErrorCode.INVALID_PARAM_CODE, Constant.ErrorCode.INVALID_PARAM_MSG, null, false);
    }

    /**
     * 数据库服务响应失败
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithDataBaseFailed() {
        return genResult(Constant.ErrorCode.DATABASE_OPERATION_ERROR_CODE, Constant.ErrorCode.DATABASE_OPERATION_ERROR_MSG, null, false);
    }

    /**
     * 数据库服务响应失败（数据已存在）
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithExistsFailed() {
        return genResult(Constant.ErrorCode.ALREADY_EXISTS_CODE, Constant.ErrorCode.ALREADY_EXISTS_ERROR_MSG, null, false);
    }

    /**
     * token验证失败
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithTokenFailed() {
        return genResult(Constant.ErrorCode.INVALID_TOKEN_CODE, Constant.ErrorCode.INVALID_TOKEN_MSG, null, false);
    }

    /**
     * 用户权限不足
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithPermissionFailed() {
        return genResult(Constant.ErrorCode.PERMISSION_DENIED_CODE, Constant.ErrorCode.PERMISSION_DENIED_MSG, null, false);
    }

    /**
     * 短信服务发送失败
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithSmsFailed() {
        return genResult(Constant.ErrorCode.SEND_SMS_ERROR_CODE, Constant.ErrorCode.SEND_SMS_ERROR_MSG, null, false);
    }

    /**
     * 手机号已经注册
     *
     * @param <T> 对象类型
     * @return
     */
    public static <T> ServiceResult<T> genResultWithPhoneFailed() {
        return genResult(Constant.ErrorCode.PHONE_EXISTS_ERROR_CODE, Constant.ErrorCode.PHONE_EXISTS_ERROR_MSG, null, true);
    }
}
