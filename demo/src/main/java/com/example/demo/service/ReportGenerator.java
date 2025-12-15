package com.example.demo.service;

import com.example.demo.dto.RedditData;
import com.example.demo.dto.YoutubeData;
import org.springframework.stereotype.Service;

@Service
public class ReportGenerator {

    public String generateYoutubeReport (YoutubeData data) {
        return "This video is about how to grow 10 feet tall in 24 hours" + data.getChannel();
    }

    public String generateRedditReport (RedditData data) {
        return "This video is about how to grow 10 feet tall in 24 hours" + data.getTitle();
    }

}
