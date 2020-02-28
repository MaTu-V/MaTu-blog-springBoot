package com.mt.blog.mapper;

import com.mt.blog.pojo.Link;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface LinkMapper {

    // 查询全部友链信息
    public LinkedList<Link> selLink();

    // 查找是否存在该友链
    public int selLinkByName(Link link);
    // 添加友链信息
    public int insLink(Link link);

    // 删除友链信息
    public int delLink(String[] ids);

    // 更新用户信息
    public int updLink(Link link);
}