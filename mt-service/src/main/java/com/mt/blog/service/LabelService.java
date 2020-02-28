package com.mt.blog.service;

import com.mt.blog.pojo.Label;
import com.mt.blog.utils.ServiceResult;

import java.util.LinkedList;

public interface LabelService {

    /**
     * 查询全部标签信息
     *
     * @return
     */
    public ServiceResult<LinkedList<Label>> selLabel();

    /**
     * 根据分类编号查询标签信息
     *
     * @param categoryId 分类id
     * @return
     */
    public ServiceResult<LinkedList<Label>> selLabelByCategoryId(String categoryId);

    /**
     * 新增标签信息
     *
     * @param label 标签数据
     * @return
     */
    public ServiceResult<Boolean> insLabel(Label label);

    /**
     * 删除选择标签以及标签文章关联表信息
     *
     * @param ids 标签ids集合
     * @return
     */
    public ServiceResult<Boolean> delLabel(String[] ids);

    /**
     * 更新标签信息
     *
     * @param label 标签数据
     * @return
     */
    public ServiceResult<Boolean> updLabel(Label label);
}
