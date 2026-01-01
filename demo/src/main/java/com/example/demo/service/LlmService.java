package com.example.demo.service;

import com.example.demo.enums.AnalysisArchetype;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonField;
import com.openai.models.ChatModel;
import com.openai.models.responses.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LlmService {

    @Value("${OPENAI_API}")
    private String apiKey;

    public String summarize(String transcript, AnalysisArchetype archetype, String customPrompt) {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();

        String prompt = buildPrompt(archetype, customPrompt);

        ResponseCreateParams params = ResponseCreateParams.builder()
        .input(prompt + transcript)
        .model(ChatModel.GPT_4_1)
        .build();

        Response response = client.responses().create(params);
        return response.output().get(0).message().get().content().get(0).asOutputText().text();
    }

    private String buildPrompt(AnalysisArchetype archetype, String customPrompt) {
        if (archetype == AnalysisArchetype.CUSTOM) {
            if (customPrompt != null && !customPrompt.isEmpty()) {
                return customPrompt;
            }
            // Fallback to ENTRY_LEVEL if custom prompt is empty
            archetype = AnalysisArchetype.ENTRY_LEVEL;
        }

        return switch (archetype) {
            case TWELVE_YEAR_OLD -> "Summarize this in simple terms that a 12-year-old would understand, in about 100 words: ";
            case ENTRY_LEVEL -> "Generate a brief summary in 50 words: ";
            case HIGH_LEVEL -> "Provide a high-level overview in one sentence: ";
            default -> "Generate a brief summary in 50 words: ";
        };
    }
}
