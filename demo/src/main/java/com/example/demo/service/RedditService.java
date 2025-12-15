package com.example.demo.service;

import com.example.demo.dto.RedditData;
import org.springframework.stereotype.Service;

@Service
public class RedditService {

    public RedditData getData() {
        RedditData mockData = new RedditData();
        mockData.setDescription("Random Description");
        mockData.setTitle("King Arthur and The Holy Knights");
        mockData.setUpvote(12);
        return mockData;

    }

}
