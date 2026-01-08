package com.example.demo.service;

import com.example.demo.enums.AnalysisArchetype;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonField;
import com.openai.models.ChatModel;
import com.openai.models.responses.*;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LlmService {

    @Value("${OPENAI_API}")
    private String apiKey;

    @Timed(value = "openai.summarize", description = "Time taken to generate summary via OpenAI")
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
            archetype = AnalysisArchetype.KEY_POINTS;
        }

        return switch (archetype) {
            case TLDR -> "Reply with exactly ONE sentence explaining why this content is worth watching. Nothing more: ";
            case KEY_POINTS -> "Extract the 3-5 most important takeaways from this content as a bullet list. Each bullet should be a complete, actionable insight: ";
            case DEEP_DIVE -> """
                    Analyze this content critically in EXACTLY 4 short sections (max 150 words total):

                    **THESIS:** One sentence stating the creator's main argument.
                    **STRENGTHS:** 2 things done well (be specific).
                    **WEAKNESSES:** 2 flaws, gaps, or counterarguments the creator missed.
                    **VERDICT:** One sentence - who should watch this and why (or why not).

                    Be direct and opinionated. This is analysis, not summary: """;
            default -> "Extract the 3-5 most important takeaways from this content as a bullet list. Each bullet should be a complete, actionable insight: ";
        };
    }
}
