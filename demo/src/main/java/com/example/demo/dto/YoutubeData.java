package com.example.demo.dto;

import lombok.Data;
import org.springframework.context.annotation.Bean;

public class YoutubeData {
    private String title;
    private String channel;
    private String duration;

    public void setTitle(String title){
        this.title = title;
    }

    public void setChannel(String channel){
        this.channel = channel;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

}

