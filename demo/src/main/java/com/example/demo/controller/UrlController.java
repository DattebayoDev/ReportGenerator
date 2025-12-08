package com.example.demo.controller;

import com.example.demo.entity.UrlRequest;
import com.example.demo.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    @Autowired
    private UrlRepository urlRepository;

    @PostMapping("/analyze")
    public String postUrl( UrlRequest url) {
        urlRepository.save(url);
        return url.getUrl();
    }
}
