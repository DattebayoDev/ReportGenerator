package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlDetector {

    @Autowired
    private YoutubeService youtubeService;

    public String detectPlatform(String url) {
        if (url.contains("youtube") || url.contains("youtu.be")) {
            return youtubeService.getData().toString();
        } else if (url.contains("reddit")) {
            return "REDDIT";
        } else {
            return "UNKNOWN";
        }
    }

}
