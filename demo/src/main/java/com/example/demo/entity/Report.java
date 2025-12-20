package com.example.demo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postId;
    private String platform;
    private String summary;
    private LocalDateTime timestamp;

    public Report(){};

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
