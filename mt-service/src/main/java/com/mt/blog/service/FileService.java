package com.mt.blog.service;

import com.mt.blog.utils.ServiceResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 图片上传
     * @param Img 图片信息
     * @param token 用户身份
     * @return
     */
    public ServiceResult<Object> uploadImg(MultipartFile Img,String token);
}
