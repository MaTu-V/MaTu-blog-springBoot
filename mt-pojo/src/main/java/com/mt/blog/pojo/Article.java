package com.mt.blog.pojo;

import java.util.LinkedList;

public class Article {
    private String id;
    private String title;
    private String summary;
    private String content;
    private String image;
    private String keywords;
    private String establishTime;
    private Integer likeCounts;
    private Integer clickCounts;
    private Integer commentCounts;
    private Integer collectionCounts;
    private User user;
    private Category categories;
    private LinkedList<Label> labels;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(Integer likeCounts) {
        this.likeCounts = likeCounts;
    }

    public Integer getClickCounts() {
        return clickCounts;
    }

    public void setClickCounts(Integer clickCounts) {
        this.clickCounts = clickCounts;
    }

    public Integer getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(Integer commentCounts) {
        this.commentCounts = commentCounts;
    }

    public Integer getCollectionCounts() {
        return collectionCounts;
    }

    public void setCollectionCounts(Integer collectionCounts) {
        this.collectionCounts = collectionCounts;
    }

    public String getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(String establishTime) {
        this.establishTime = establishTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategories() {
        return categories;
    }

    public void setCategories(Category categories) {
        this.categories = categories;
    }


    public LinkedList<Label> getLabels() {
        return labels;
    }

    public void setLabels(LinkedList<Label> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", keywords='" + keywords + '\'' +
                ", establishTime='" + establishTime + '\'' +
                ", likeCounts=" + likeCounts +
                ", clickCounts=" + clickCounts +
                ", commentCounts=" + commentCounts +
                ", collectionCounts=" + collectionCounts +
                ", user=" + user +
                ", categories=" + categories +
                ", labels=" + labels +
                '}';
    }
}
