package com.mt.blog.service.Impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mt.blog.service.TokenService;
import com.mt.blog.utils.ReadProteriesUtil;
import com.mt.blog.utils.ServiceResult;
import com.mt.blog.utils.ServiceResultHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger LOG = LoggerFactory.getLogger(TokenServiceImpl.class);
    @Autowired
    private ReadProteriesUtil readProteriesUtil;


    // 秘钥生成
    private byte[] getSecret() {

        return readProteriesUtil.tokenSecret.getBytes();
    }

    /**
     * 生成token
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public ServiceResult<String> getToken(String userId) {
        // 用户信息不存在 无法生成token
        if (StringUtils.isEmpty(userId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 设置token过期时间（15天过期）
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 10);
        try {
            // 设置私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(getSecret());
            // 设置jwt头部信息
            Map<String, Object> jwtHeader = new HashMap<>(2);
            jwtHeader.put("Type", "Jwt");
            jwtHeader.put("alg", "HS256");
            // 将用户id和随机数做为加密
            String token = JWT.create().  // 创建JWTCreator实例
                    withHeader(jwtHeader).  // 设置header
                    withClaim("uId", userId)  // 设置payload
                    .withExpiresAt(calendar.getTime())  // 设置过期时间
                    .withIssuedAt(now)  // 设置生成时间
                    .sign(algorithm);  // 具体算法加密
            if (!StringUtils.isEmpty(token)) {
                return ServiceResultHelper.genResultWithTokenSuccess(token);
            }
            return ServiceResultHelper.genResultWithTokenFailed();
        } catch (Exception e) {
            return ServiceResultHelper.genResultWithTokenFailed();
        }
    }

    /**
     * 验证token
     *
     * @param token token验证
     * @return
     */
    @Override
    public ServiceResult<Boolean> checkToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(getSecret()); // 算法加密
            JWTVerifier verifier = JWT.require(algorithm).build(); // 检测
            DecodedJWT jwt = verifier.verify(token); // 解密内容
            return ServiceResultHelper.genResultWithTokenSuccess(true);
        } catch (TokenExpiredException e) {
            // 解密失败 token已过期 (直接返回自动登录)
            return ServiceResultHelper.genResult(-1000, "TOKEN已经过期", false);
        } catch (Exception e) {
            // e.printStackTrace();
            return ServiceResultHelper.genResultWithTokenFailed();
        }
    }
}