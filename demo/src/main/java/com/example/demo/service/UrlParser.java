package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class UrlParser {

    public String parseYoutubeUrl(String url) {
        String extractedId;
        String[] lengthOfString;
        if (url.contains("=")) {
            extractedId = url.split("=")[1];
        } else {
            lengthOfString = url.split("/");
            extractedId =  lengthOfString[lengthOfString.length - 1];
        }
        return extractedId;
    }

    public String parseRedditUrl(String url) {
        return  url.split("/")[6];
    }

}
