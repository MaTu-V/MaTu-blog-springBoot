package com.mt.blog.controller;

import com.mt.blog.pojo.Category;
import com.mt.blog.service.CategoryService;
import com.mt.blog.utils.BlogJSONResult;
import com.mt.blog.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BlogJSONResult result;

    /**
     * 获取分类信息
     * @return
     */
    @GetMapping("/getCategory")
    public BlogJSONResult getCategory() {
        return result.ok(categoryService.selCategory().getData()); // 返回数据
    }

    /**
     * 插入分类信息
     * @param category 分类信息
     * @return
     */
    @PostMapping("/insCategory")
    public BlogJSONResult insCategory(@RequestBody Category category) {
        ServiceResult<Boolean> serviceResult = categoryService.insCategory(category); // 插入
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回数据
    }

    /**
     * 删除分类信息
     * @param ids 分类ids数组
     * @return
     */
    @GetMapping("/delCategory")
    public BlogJSONResult delCategory(String[] ids) {
        ServiceResult<Boolean> serviceResult = categoryService.delCategory(ids); // 删除
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回数据
    }

    /**
     * 更新分类信息
     * @param category 分类信息
     * @return
     */
    @PostMapping("/updCategory")
    public BlogJSONResult updCategory(@RequestBody Category category) {
        ServiceResult<Boolean> serviceResult = categoryService.updCategory(category); // 更新
        return serviceResult.isSuccess() ? result.ok() : result.error(serviceResult.getCode(), serviceResult.getMsg()); // 返回数据
    }
}