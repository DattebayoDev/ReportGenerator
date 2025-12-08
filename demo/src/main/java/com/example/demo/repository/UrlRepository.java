package com.example.demo.repository;

import com.example.demo.entity.UrlRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlRequest, Long> {
}
