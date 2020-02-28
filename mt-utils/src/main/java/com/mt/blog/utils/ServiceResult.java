package com.mt.blog.utils;

import org.springframework.stereotype.Component;

@Component
public class ServiceResult<T> {
    private int code;
    private String msg;
    private T data;
    private boolean success;

    public ServiceResult() {

    }

    public ServiceResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ServiceResult(boolean success) {
        this.success = success;
    }

    public static <T> ServiceResult<T> create() {
        return new ServiceResult<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ServiceResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }
}