package com.mt.blog.service.Impl;

import com.auth0.jwt.JWT;
import com.mt.blog.service.FileService;
import com.mt.blog.utils.FileUploadUtil;
import com.mt.blog.utils.ServiceResult;
import com.mt.blog.utils.ServiceResultHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);
    @Autowired
    FileUploadUtil fileUploadUtil;
    /**
     * 图片上传
     *
     * @param Img   图片信息
     * @param token 用户身份
     * @return
     */
    @Override
    public ServiceResult<Object> uploadImg(MultipartFile Img, String token) {
        ServiceResult<String> serviceResult = getUserId(token);
        String userId = serviceResult.isSuccess() ? serviceResult.getData() : null;
        // 从token中提取用户id信息失败
        if (StringUtils.isEmpty(userId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        //获取当前日期
        Date now = new Date();
        // 设定日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //定义上传书籍的文件命名空间
        String fileSpace = "/" +  userId;
        // 保存到数据库的目录
        String uploadPathDB = "/" + dateFormat.format(now) + "/Imgs";
        // 上传书籍封面图片（拿到最终返回的路径）
        String upload = fileUploadUtil.uploadResources(Img, fileSpace, uploadPathDB);
        if (!StringUtils.isEmpty(upload)){
            return ServiceResultHelper.genResultWithSuccess((Object)upload);
        }
        return ServiceResultHelper.genResultWithFailed("图片上传失败!");
    }
    /**
     * 从token中提取用户id信息
     *
     * @param token 用户id
     * @return
     */
    private ServiceResult<String> getUserId(String token) {
        // 从请求体中取出用户信息
        // 获取用户token
        if (StringUtils.isEmpty(token)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        try {
            String userId = JWT.decode(token).getClaim("uId").asString(); // 从token中取出用户id
            return ServiceResultHelper.genResultWithTokenSuccess(userId);
        } catch (Exception e) {
            // e.printStackTrace();
            return ServiceResultHelper.genResultWithTokenFailed();
        }
    }
}
