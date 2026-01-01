package com.example.demo.dto;

import com.example.demo.enums.AnalysisArchetype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class AnalysisRequest {
    private String url;
    private AnalysisArchetype archetype;
    private String customPrompt;
}
