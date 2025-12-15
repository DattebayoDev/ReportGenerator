package com.example.demo.controller;

import com.example.demo.dto.YoutubeData;
import com.example.demo.entity.UrlRequest;
import com.example.demo.repository.UrlRepository;
import com.example.demo.service.RedditService;
import com.example.demo.service.ReportGenerator;
import com.example.demo.service.UrlDetector;
import com.example.demo.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class UrlController {

    @Autowired
    private UrlDetector urlDetector;

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private YoutubeService youtubeService;

    @Autowired
    private RedditService redditService;

    @Autowired
    private ReportGenerator reportGenerator;

    @PostMapping("/analyze")
    public String postUrl(@RequestBody UrlRequest url) {
        urlRepository.save(url);
        String website = url.getUrl();
        if (Objects.equals(urlDetector.detectPlatform(website), "YOUTUBE")) {
           return reportGenerator.generateYoutubeReport(youtubeService.getData());
        } else if (Objects.equals(urlDetector.detectPlatform(website), "REDDIT")) {
            return reportGenerator.generateRedditReport(redditService.getData());
        }
        return "UNKNOWN";
    }


    @GetMapping("/analyze")
    public List<UrlRequest> getUrl() {
        return urlRepository.findAll();
    }
}
