package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postId;
    private String platform;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private LocalDateTime timestamp;

    public Report(){};
}
