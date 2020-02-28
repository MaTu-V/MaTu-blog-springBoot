package com.mt.blog.pojo;

public class Label {
    private Integer id;
    private String labelName;
    private String back;
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", labelName='" + labelName + '\'' +
                ", back='" + back + '\'' +
                ", category=" + category +
                '}';
    }

}