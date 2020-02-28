package com.mt.blog.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloWorld{

    /**
     * 测试连接服务器
     * @return
     */
    @GetMapping({"/getHello","/","/index"})
    public String getHello(){
        return "HelloWorld";
    }
}