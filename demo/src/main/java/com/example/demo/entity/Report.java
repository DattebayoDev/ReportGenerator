package com.example.demo.entity;


import com.example.demo.enums.AnalysisArchetype;
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

    @Enumerated(EnumType.STRING)
    private AnalysisArchetype archetype;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private LocalDateTime timestamp;

    public Report(){};
}
