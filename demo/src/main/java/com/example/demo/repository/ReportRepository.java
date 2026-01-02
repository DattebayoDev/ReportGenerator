package com.example.demo.repository;

import com.example.demo.entity.Report;
import com.example.demo.enums.AnalysisArchetype;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository <Report, Long> {
    Report getByPostId(String postId);

    Report findByPostId(String postId);

    Report findByPostIdAndArchetype(String postId, AnalysisArchetype archetype);

    List<Report> findAllByPlatform (String platform);
}
