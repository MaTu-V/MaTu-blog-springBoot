package com.mt.blog.mapper;

import com.mt.blog.pojo.Label;
import com.mt.blog.pojo.Link;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface LabelMapper {

    // 查询全部标签信息
    public LinkedList<Label> selLabel();
    // 查询全部标签数据信息
    public LinkedList<Label> selLabelByCategoryId(String categoryId);
    // 添加标签信息
    public int insLabel(Label label);

    // 删除标签信息(同时删除关联表中信息)
    public int delLabel(String[] ids);

    // 更新当前标签信息
    public int updLabel(Label label);

}
