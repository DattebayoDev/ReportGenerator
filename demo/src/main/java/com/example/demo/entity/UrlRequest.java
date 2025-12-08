package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Entity
public class UrlRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    String url;

    public String getUrl(){
        return url;
    }


}
