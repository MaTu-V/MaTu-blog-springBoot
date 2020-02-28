package com.mt.blog.utils;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
public class AESUtil {

    @Autowired
    ReadProteriesUtil readProteriesUtil;

    private byte[] getKey() {
        return readProteriesUtil.aesSecret.getBytes();
    }

    /**
     * AES解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String Decrypt(String data) {
        try {
            byte[] content = Hex.decodeHex(data.toCharArray());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //因为AES的加密块大小是128bit(16byte), 所以key是128、192、256bit无关
            SecretKeySpec secretKeySpec = new SecretKeySpec(getKey(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(getKey()));
            return new String(cipher.doFinal(content));
        } catch (Exception e) {
            // TODO:解密写入到日志中
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
//        AESUtil aesUtil = new AESUtil();
//        //传给crypto的key、iv要使用base64格式
//        String crypto = "86e748e1dff2b63d7f9ade02c07f2d9d";
//        byte[] data = Hex.decodeHex(crypto.toCharArray());
//        System.out.println(aesUtil.Decrypt(crypto));
    }

}
