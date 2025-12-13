package com.example.demo.controller;

import com.example.demo.entity.UrlRequest;
import com.example.demo.repository.UrlRepository;
import com.example.demo.service.UrlDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UrlController {

    @Autowired
    private UrlDetector urlDetector;

    @Autowired
    private UrlRepository urlRepository;

    @PostMapping("/analyze")
    public String postUrl(@RequestBody UrlRequest url) {
        urlRepository.save(url);
        return urlDetector.detectPlatform(url.getUrl());
    }


    @GetMapping("/analyze")
    public List<UrlRequest> getUrl() {
        return urlRepository.findAll();
    }
}
