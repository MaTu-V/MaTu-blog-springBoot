package com.mt.blog.utils;

import org.springframework.stereotype.Component;

/**
 * 分页工具类
 */
@Component
public class PagedUtil<T> {

    private int page;            // 当前页数
    private int total;            // 总数据量
    private int pageSize;
    private long records;        // 总页数
    private T rows;        // 每页的数据数


    public PagedUtil() {

    }

    public static <T> PagedUtil<T> create() {
        return new PagedUtil<>();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }
}
