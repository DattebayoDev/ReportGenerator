package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class UrlParser {

    public String parseYoutubeUrl(String url) {
        String extractedId;
        if (url.contains("=")) {
            extractedId = url.split("=")[1];
        } else {
            extractedId = url.split("/")[1];
        }
        return extractedId;
    }

}
