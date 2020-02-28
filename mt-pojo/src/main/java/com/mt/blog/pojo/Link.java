package com.mt.blog.pojo;

public class Link {
    private String id;
    private String author;
    private String webName;
    private String url;
    private String describe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", webName='" + webName + '\'' +
                ", url='" + url + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
