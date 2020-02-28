package com.mt.blog.service;

import com.mt.blog.pojo.Link;
import com.mt.blog.utils.ServiceResult;

import java.util.LinkedList;

public interface LinkService {

    /**
     * 查找全部友链
     *
     * @return
     */
    public ServiceResult<LinkedList<Link>> selLink();

    /**
     * 添加友链
     *
     * @param link 友链数据
     * @return
     */
    public ServiceResult<Boolean> insLink(Link link);

    /**
     * 删除友链
     *
     * @param ids 待删除友链ids
     * @return
     */
    public ServiceResult<Boolean> delLink(String[] ids);

    /**
     * 更新友链
     *
     * @param link 友链数据
     * @return
     */
    public ServiceResult<Boolean> updLink(Link link);
}