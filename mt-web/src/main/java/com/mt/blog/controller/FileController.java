package com.mt.blog.controller;

import com.auth0.jwt.JWT;
import com.mt.blog.service.FileService;
import com.mt.blog.utils.BlogJSONResult;
import com.mt.blog.utils.FileUploadUtil;
import com.mt.blog.utils.ServiceResult;
import com.mt.blog.utils.ServiceResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    FileUploadUtil fileUploadUtil;
    @Autowired
    FileService fileService;
    @Autowired
    BlogJSONResult result;

    /**
     * 上传图片
     *
     * @param Img 图片文件
     * @return
     */
    @PostMapping("/uploadImg")
    public BlogJSONResult uploadImg(@RequestParam("Img") MultipartFile Img) {
        String token = httpServletRequest.getHeader("public-token"); // 获取用户身份 token
        ServiceResult<Object> serviceResult = fileService.uploadImg(Img,token); // 插入
        return serviceResult.isSuccess() ? result.ok(serviceResult.getData()) : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回状态信息

    }

}
