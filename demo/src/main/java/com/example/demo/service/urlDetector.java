package com.example.demo.service;

public class urlDetector {

    private String url;

    public String detectPlatform(String Url) {
        if (Url.contains("youtube")) {
            return "YOUTUBE";
        } else if (Url.contains("reddit")) {
            return "REDDIT";
        } else {
            return "UNKNOWN";
        }
    }

}
