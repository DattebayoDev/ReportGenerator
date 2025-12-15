package com.example.demo.service;

import com.example.demo.dto.YoutubeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlDetector {

    public String detectPlatform(String url) {
        if (url.contains("youtube") || url.contains("youtu.be")) {
            return "YOUTUBE";
        } else if (url.contains("reddit")) {
            return "REDDIT";
        } else {
            return "UNKNOWN";
        }
    }

}
