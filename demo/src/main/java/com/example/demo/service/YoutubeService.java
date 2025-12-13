package com.example.demo.service;

import com.example.demo.dto.YoutubeData;
import org.springframework.stereotype.Service;

@Service
public class YoutubeService {

  public YoutubeData getData(){
    YoutubeData mockData = new YoutubeData();
    mockData.setTitle("How to Code");
    mockData.setDuration("10:00");
    mockData.setChannel("Code with Ang");
    return mockData;
  }
}
