package com.mt.blog.controller;

import com.mt.blog.pojo.Label;
import com.mt.blog.service.LabelService;
import com.mt.blog.utils.BlogJSONResult;
import com.mt.blog.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;
    @Autowired
    private BlogJSONResult result;

    /**
     * 获取标签信息
     * @return
     */
    @GetMapping("/getLabel")
    public BlogJSONResult getLabel() {
        return result.ok(labelService.selLabel().getData()); // 获取数据
    }

    /**
     * 获取某个分类信息下的所有标签
     * @param categoryId 分类id
     * @return
     */
    @GetMapping("/getLabelByCategoryId/{categoryId}")
    public BlogJSONResult getLabelByCategoryId(@PathVariable("categoryId") String categoryId) {
        return result.ok(labelService.selLabelByCategoryId(categoryId).getData()); // 获取数据
    }

    /**
     * 插入标签
     * @param label 标签信息
     * @return
     */
    @PostMapping("/insLabel")
    public BlogJSONResult insLabel(@RequestBody Label label) {
        ServiceResult<Boolean> serviceResult = labelService.insLabel(label); // 插入
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg()); //返回信息
    }

    /**
     * 删除标签
     * @param ids 标签ids数组
     * @return
     */
    @GetMapping("/delLabel")
    public BlogJSONResult delLabel(String[] ids) {
        ServiceResult<Boolean> serviceResult = labelService.delLabel(ids); // 删除
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回数据
    }

    /**
     * 更新标签
     * @param label 标签信息
     * @return
     */
    @PostMapping("/updLabel")
    public BlogJSONResult updLabel(@RequestBody Label label) {
        ServiceResult<Boolean> serviceResult = labelService.updLabel(label); // 更新
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回数据
    }

}