package com.mt.blog.mapper;

import com.mt.blog.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface CategoryMapper {

    // 查找全部分类信息
    public LinkedList<Category> selCategory();

    // 查找是否存在该分类名
    public int selCategoryByName(Category category);
    // 查找指定分类信息
    public Category selCategoryById(int id);
    // 添加分类信息
    public int insCategory(Category category);

    // 删除分类信息
    public int delCategory(String[] ids);

    // 更新分类信息
    public int updCategory(Category category);
}
