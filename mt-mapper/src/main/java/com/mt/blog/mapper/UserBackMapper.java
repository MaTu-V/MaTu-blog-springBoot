package com.mt.blog.mapper;

import com.mt.blog.pojo.UserBack;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface UserBackMapper {

    // 查询全部背景图信息
    public LinkedList<UserBack> selUserBack();

    // 添加背景图信息
    public int insUserBack(UserBack userBack);

    // 删除背景图信息
    public int delUserBack(String[] ids);

    // 修改背景图信息
    public int updUserBack(UserBack userBack);
}
