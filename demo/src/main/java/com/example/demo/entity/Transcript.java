package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setTranscriptText(String transcriptText) {
        this.transcriptText = transcriptText;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
