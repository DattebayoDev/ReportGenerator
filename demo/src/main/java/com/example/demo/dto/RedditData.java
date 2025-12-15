package com.example.demo.dto;

public class RedditData {

    private String title;
    private String description;
    private Integer upvote;

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getUpvote() {
        return upvote;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUpvote(Integer upvote) {
        this.upvote = upvote;
    }
}
