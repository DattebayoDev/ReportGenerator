package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Transcript {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoId;

    @Column(columnDefinition = "TEXT")
    private String transcriptText;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;

    public Transcript() {}
}
