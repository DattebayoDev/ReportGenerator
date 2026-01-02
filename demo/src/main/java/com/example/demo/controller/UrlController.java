package com.example.demo.controller;

import com.example.demo.dto.AnalysisRequest;
import com.example.demo.entity.Report;
import com.example.demo.entity.Transcript;
import com.example.demo.repository.ReportRepository;
import com.example.demo.repository.TranscriptRepository;
import com.example.demo.service.*;
import io.github.thoroldvix.api.TranscriptRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
public class UrlController {

    @Autowired
    private UrlDetector urlDetector;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private TranscriptRepository transcriptRepository;

    @Autowired
    private YoutubeService youtubeService;

    @Autowired
    private LlmService llmService;

    @Autowired
    private RedditService redditService;

    @Autowired
    private UrlParser urlParser;

    @Autowired
    private ReportGenerator reportGenerator;

    @PostMapping("/analyze")
    public Report postUrl(@RequestBody AnalysisRequest analysisRequest) throws TranscriptRetrievalException {
        String postId;
        String platform = urlDetector.detectPlatform(analysisRequest.getUrl());
        if (Objects.equals(platform, "YOUTUBE")) {
            postId = urlParser.parseYoutubeUrl(analysisRequest.getUrl());
        } else {
            postId = urlParser.parseRedditUrl(analysisRequest.getUrl());
        }

        if (reportRepository.findByPostIdAndArchetype(postId, analysisRequest.getArchetype()) != null) {
            return reportRepository.findByPostIdAndArchetype(postId, analysisRequest.getArchetype());
        }

        Report report = new Report();
        Transcript transcript = new Transcript();

        if (Objects.equals(platform, "YOUTUBE")) {
            report.setPostId(postId);
            report.setPlatform(platform);
            report.setArchetype(analysisRequest.getArchetype());
            report.setTimestamp(LocalDateTime.now());
            transcript.setTranscriptText(youtubeService.getTranscript(postId).getTranscript());;
            report.setSummary(llmService.summarize(transcript.getTranscriptText(), analysisRequest.getArchetype(), analysisRequest.getCustomPrompt()));
            reportRepository.save(report);
            transcript.setReport(report);
            transcriptRepository.save(transcript);
        } else if (Objects.equals(platform, "REDDIT")) {
            report.setPostId(postId);
            report.setPlatform(platform);
            report.setArchetype(analysisRequest.getArchetype());
            report.setSummary(reportGenerator.generateRedditReport(redditService.getData()));
            report.setTimestamp(LocalDateTime.now());
            reportRepository.save(report);
        }
        return report;
    }

    @GetMapping("/reports")
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @GetMapping("/transcripts")
    public List<Transcript> getAllTranscripts() {
        return transcriptRepository.findAll();
    }

    @GetMapping("/reports/{id}")
    public Report getReport(@PathVariable("id") String id ){
        return reportRepository.findByPostId(id);
    }

    @DeleteMapping("/reports/{id}")
    public void deleteReport(@PathVariable("id") String id){
        Report report = reportRepository.findByPostId(id);
        if (report != null) {
            reportRepository.deleteById(report.getId());
        }
    }

    @GetMapping("/reports/platform")
    public List<Report> getReports(@RequestParam("website") String website) {
        String convertedPlatform = website.toUpperCase();
        return reportRepository.findAllByPlatform(convertedPlatform);
    }
}
