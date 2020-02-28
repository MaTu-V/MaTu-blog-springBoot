package com.mt.blog.pojo;

import java.util.Date;

public class Website {
    private Integer id;
    private String content;
    private Date establishTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(Date establishTime) {
        this.establishTime = establishTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Website{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", establishTime=" + establishTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
