package com.mt.blog.controller;

import com.mt.blog.service.MailService;
import com.mt.blog.utils.BlogJSONResult;
import com.mt.blog.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private BlogJSONResult result;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private MailService mailService;

    @GetMapping("/bindEmail")
    public BlogJSONResult bindEmail(@RequestParam("email") String email,@RequestParam("url") String url){
        String token = httpServletRequest.getHeader("public-token");
        ServiceResult<Boolean> serviceResult = mailService.sendBindMail(email,url);
        return serviceResult.isSuccess()? result.ok() : result.error(serviceResult.getCode(),serviceResult.getMsg());
    }
}
