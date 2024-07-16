package com.javainuse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String username;
    @Column
    private LocalDateTime  createdate;
    @Column
    private LocalDateTime modifydate;
    @Column
    private String title;
    @Lob
    @Column(columnDefinition = "longblob")
    private String text;
    @Column
    private boolean privatePost;


    public String getText() {
        return text;
    }

    public LocalDateTime getModifydate() {
        return modifydate;
    }

    public LocalDateTime getCreatedate() {
        return createdate;
    }

    public String getUser() {
        return username;
    }

    public long getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setModifydate(LocalDateTime modifydate) {
        this.modifydate = modifydate;
    }

    public void setCreatedate(LocalDateTime createdate) {
        this.createdate = createdate;
    }

    public void setUser(String user) {
        this.username = user;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPrivatePost() {
        return privatePost;
    }

    public void setPrivatePost(boolean privatePost) {
        this.privatePost = privatePost;
    }
}
