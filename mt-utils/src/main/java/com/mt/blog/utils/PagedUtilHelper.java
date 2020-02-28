package com.mt.blog.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class PagedUtilHelper {

    public PagedUtilHelper() {
    }

    public static <T> PagedUtil<T> genResultPagedData(T data, Page page) {
        //返回自定义的分页数据集合（收集数据）
        PagedUtil<T> result = PagedUtil.create();
        //记录当前页
        result.setPage(page.getPageNum());
        // 全部数据总数
        result.setTotal((int)page.getTotal());
        // 当前页面大小
        result.setPageSize(page.getPageSize());
        // 总页面数
        result.setRecords(page.getPages());
        // 数据内容
        result.setRows(data);
        return result;
    }
}
