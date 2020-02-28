package com.mt.blog.service;

import com.mt.blog.pojo.Category;
import com.mt.blog.utils.ServiceResult;

import java.util.LinkedList;

public interface CategoryService {

    /**
     * 查询全部分类信息
     *
     * @return
     */
    public ServiceResult<LinkedList<Category>> selCategory();

    /**
     * 添加分类信息
     *
     * @param category 分类对象
     * @return
     */
    public ServiceResult<Boolean> insCategory(Category category);

    /**
     * 删除分类集合信息
     *
     * @param ids 集合ids
     * @return
     */
    public ServiceResult<Boolean> delCategory(String[] ids);

    /**
     * 更新分类信息
     *
     * @param category 分类对象
     * @return
     */
    public ServiceResult<Boolean> updCategory(Category category);
}
