package com.javainuse.model;

public class PostDTO {

    private String title;
    private String text;
    private boolean privatePost;

    public PostDTO() {
        this.text="";
        this.title="";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPrivatePost() {
        return privatePost;
    }

    public void setPrivatePost(boolean privatePost) {
        this.privatePost = privatePost;
    }

}
