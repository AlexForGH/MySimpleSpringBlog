package org.example.model;

import java.util.List;

public class Post {
    private Long id;
    private String title;
    private String imagePath;
    private int likesCount;
    private String text;
    private List<String> tags;
    private List<Comment> comments;

    public Post() {
    }

    public Post(Long id, String title, String imagePath, int likesCount, String text, List<String> tags) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.likesCount = likesCount;
        this.text = text;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
