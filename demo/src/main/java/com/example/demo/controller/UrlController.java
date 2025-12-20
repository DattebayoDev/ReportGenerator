package com.example.demo.controller;

import com.example.demo.dto.YoutubeData;
import com.example.demo.entity.Report;
import com.example.demo.entity.UrlRequest;
import com.example.demo.repository.ReportRepository;
import com.example.demo.repository.UrlRepository;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    private YoutubeService youtubeService;

    @Autowired
    private RedditService redditService;

    @Autowired
    private UrlParser urlParser;

    @Autowired
    private ReportGenerator reportGenerator;

    @PostMapping("/analyze")
    public Report postUrl(@RequestBody UrlRequest urlRequest) {
        String url;
        String platform = urlDetector.detectPlatform(urlRequest.getUrl());
        if (Objects.equals(platform, "YOUTUBE")) {
            url = urlParser.parseYoutubeUrl(urlRequest.getUrl());
        } else {
            url = urlParser.parseRedditUrl(urlRequest.getUrl());
        }

        if (reportRepository.findByUrl(url) != null) {
            Report report = reportRepository.getByUrl(url);
            return reportRepository.getByUrl(url);
        }

        Report report = new Report();
        if (Objects.equals(platform, "YOUTUBE")) {
            report.setUrl(url);
            report.setPlatform(platform);
            report.setSummary(reportGenerator.generateYoutubeReport(youtubeService.getData()));
            report.setTimestamp(LocalDateTime.now());
            reportRepository.save(report);
        } else if (Objects.equals(platform, "REDDIT")) {
            report.setUrl(url);
            report.setPlatform(platform);
            report.setSummary(reportGenerator.generateRedditReport(redditService.getData()));
            report.setTimestamp(LocalDateTime.now());
            reportRepository.save(report);
        }
        return report;
    }


    @GetMapping("/analyze")
    public List<UrlRequest> getUrl() {
        return urlRepository.findAll();
    }
}
