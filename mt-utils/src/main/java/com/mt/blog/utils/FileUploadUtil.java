package com.mt.blog.utils;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class FileUploadUtil {

    @Autowired
    ReadProteriesUtil readProteriesUtil;
    /**
     * 资源文件上传
     * @param file
     * @param fileSpace
     * @param uploadPathDB
     * @return
     */
    public String uploadResources(MultipartFile file, String fileSpace, String uploadPathDB) {
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (file != null) {
                //生成文件名称
                String FileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                //文件名不为空
                if (!StringUtils.isEmpty(FileName)) {
                    // 文件上传的最终保存路径
                    String finalPath = readProteriesUtil.FILE_SPACE + fileSpace + uploadPathDB + "/" + FileName;
                    // 设置数据库保存的路径
                    uploadPathDB = fileSpace + uploadPathDB + "/" + FileName;
                    File outFile = new File(finalPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            }
            //上传成功后返回文件路径
            return uploadPathDB;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
