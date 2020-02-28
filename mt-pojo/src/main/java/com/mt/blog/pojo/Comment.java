package com.mt.blog.pojo;


public class Comment {
    private String id; // 评论id
    private String content; // 评论内容
    private String commentTime; // 评论时间
    private Integer likeCounts; // 点赞数量
    private Integer state; // 评论状态
    private User reviewerUser; // 发表人
    private User replyUser; // 回复人
    private String articleId; // 文章id
    private String parentId; // 父评论id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(Integer likeCounts) {
        this.likeCounts = likeCounts;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public User getReviewerUser() {
        return reviewerUser;
    }

    public void setReviewerUser(User reviewerUser) {
        this.reviewerUser = reviewerUser;
    }

    public User getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(User replyUser) {
        this.replyUser = replyUser;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", commentTime='" + commentTime + '\'' +
                ", likeCounts=" + likeCounts +
                ", state=" + state +
                ", reviewerUser=" + reviewerUser +
                ", replyUser=" + replyUser +
                ", articleId='" + articleId + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
