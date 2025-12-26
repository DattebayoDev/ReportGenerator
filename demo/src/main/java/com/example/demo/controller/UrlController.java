package com.example.demo.controller;

import com.example.demo.entity.Report;
import com.example.demo.entity.Transcript;
import com.example.demo.entity.UrlRequest;
import com.example.demo.repository.ReportRepository;
import com.example.demo.repository.TranscriptRepository;
import com.example.demo.repository.UrlRepository;
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
    private UrlRepository urlRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private TranscriptRepository transcriptRepository;

    @Autowired
    private YoutubeService youtubeService;

    @Autowired
    private RedditService redditService;

    @Autowired
    private UrlParser urlParser;

    @Autowired
    private ReportGenerator reportGenerator;

    @PostMapping("/analyze")
    public Report postUrl(@RequestBody UrlRequest urlRequest) throws TranscriptRetrievalException {
        String postId;
        String platform = urlDetector.detectPlatform(urlRequest.getUrl());
        if (Objects.equals(platform, "YOUTUBE")) {
            postId = urlParser.parseYoutubeUrl(urlRequest.getUrl());
        } else {
            postId = urlParser.parseRedditUrl(urlRequest.getUrl());
        }

        if (reportRepository.findByPostId(postId) != null) {
            return reportRepository.getByPostId(postId);
        }

        Report report = new Report();
        Transcript transcript = new Transcript();

        if (Objects.equals(platform, "YOUTUBE")) {
            report.setPostId(postId);
            report.setPlatform(platform);
            report.setSummary(reportGenerator.generateYoutubeReport(youtubeService.getData(postId)));
            report.setTimestamp(LocalDateTime.now());
            transcript.setTranscriptText(youtubeService.getTranscript(postId).getTranscript());
            reportRepository.save(report);
            transcript.setReport(report);
            transcriptRepository.save(transcript);
        } else if (Objects.equals(platform, "REDDIT")) {
            report.setPostId(postId);
            report.setPlatform(platform);
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

    @GetMapping("/analyze")
    public List<UrlRequest> getUrl() {
        return urlRepository.findAll();
    }
}
