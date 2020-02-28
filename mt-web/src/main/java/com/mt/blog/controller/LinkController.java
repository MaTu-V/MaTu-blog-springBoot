package com.mt.blog.controller;

import com.mt.blog.pojo.Link;
import com.mt.blog.service.LinkService;
import com.mt.blog.utils.BlogJSONResult;
import com.mt.blog.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;
    @Autowired
    private BlogJSONResult result;

    /**
     * 返回友链信息
     * @return
     */
    @GetMapping("/getLink")
    public BlogJSONResult getLink(){
        return result.ok(linkService.selLink().getData()); // 返回数据
    }

    /**
     * 插入友链信息
     * @param link 友链
     * @return
     */
    @PostMapping("/insLink")
    public BlogJSONResult insLink(@RequestBody Link link){
        ServiceResult<Boolean> serviceResult = linkService.insLink(link); // 插入
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg());// 返回数据
    }

    /**
     * 删除友链
     * @param ids 友链ids数组
     * @return
     */
    @GetMapping("/delLink")
    public BlogJSONResult delLink(String[] ids){
        ServiceResult<Boolean> serviceResult = linkService.delLink(ids); // 删除
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg());// 返回数据
    }

    /**
     * 更新友链信息
     * @param link 友链
     * @return
     */
    @PostMapping("/updLink")
    public BlogJSONResult updLink(@RequestBody Link link){
        ServiceResult<Boolean> serviceResult = linkService.updLink(link);// 更新
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg());// 返回数据
    }
}