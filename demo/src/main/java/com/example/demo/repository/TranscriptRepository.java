package com.example.demo.repository;

import com.example.demo.entity.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long> {

    Optional<Transcript> findByVideoId(String videoId);
}
