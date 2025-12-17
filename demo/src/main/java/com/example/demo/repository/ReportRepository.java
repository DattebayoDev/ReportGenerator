package com.example.demo.repository;

import com.example.demo.entity.Report;
import com.example.demo.entity.UrlRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository <Report, Long> {
    Report findByUrl(String url);

    Report getByUrl(String url);
}
