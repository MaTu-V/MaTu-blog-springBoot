package com.mt.blog.service;

import com.mt.blog.utils.ServiceResult;

public interface TokenService {
    /**
     * 根据用户id生成token
     * @param userId 用户id标识
     * @return
     */
    public ServiceResult<String> getToken(String userId);

    /**
     * 验证token
     * @param token 待检测的用户token
     * @return
     */
    public ServiceResult<Boolean> checkToken(String token);
}