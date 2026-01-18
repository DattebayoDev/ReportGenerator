package com.example.demo.service;

import com.example.demo.dto.AnalysisResult;
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
    public AnalysisResult summarize(String transcript, String comments, AnalysisArchetype archetype, String customPrompt) {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();

        String prompt = buildPrompt(archetype, customPrompt);
        String input = prompt + "\n\nVIDEO TRANSCRIPT:\n" + transcript + "\n\nCOMMUNITY COMMENTS:\n" + comments;

        ResponseCreateParams params = ResponseCreateParams.builder()
        .input(input)
        .model(ChatModel.GPT_4_1)
        .build();

        Response response = client.responses().create(params);
        String fullResponse = response.output().get(0).message().get().content().get(0).asOutputText().text();

        return parseResponse(fullResponse);
    }

    private AnalysisResult parseResponse(String response) {
        String delimiter = "---COMMUNITY REACTION---";

        if (response.contains(delimiter)) {
            String[] parts = response.split(delimiter, 2);
            return new AnalysisResult(
                parts[0].trim(),
                parts.length > 1 ? parts[1].trim() : ""
            );
        }

        // Fallback if delimiter not found - return all as content summary
        return new AnalysisResult(response.trim(), "No community reaction available.");
    }

    private String buildPrompt(AnalysisArchetype archetype, String customPrompt) {
        if (archetype == AnalysisArchetype.CUSTOM) {
            if (customPrompt != null && !customPrompt.isEmpty()) {
                return customPrompt + "\n\nThen add '---COMMUNITY REACTION---' on a new line, followed by a 1-2 sentence summary of the community's reaction based on the comments.";
            }
            // Fallback to ENTRY_LEVEL if custom prompt is empty
            archetype = AnalysisArchetype.KEY_POINTS;
        }

        String basePrompt = switch (archetype) {
            case TLDR -> "Reply with exactly ONE sentence explaining why this content is worth watching.";
            case KEY_POINTS -> "Extract the 3-5 most important takeaways from this content as a bullet list. Each bullet should be a complete, actionable insight.";
            case DEEP_DIVE -> """
                    Analyze this content critically in EXACTLY 4 short sections (max 150 words total):

                    **THESIS:** One sentence stating the creator's main argument.
                    **STRENGTHS:** 2 things done well (be specific).
                    **WEAKNESSES:** 2 flaws, gaps, or counterarguments the creator missed.
                    **VERDICT:** One sentence - who should watch this and why (or why not).

                    Be direct and opinionated. This is analysis, not summary.""";
            default -> "Extract the 3-5 most important takeaways from this content as a bullet list. Each bullet should be a complete, actionable insight.";
        };

        return basePrompt + "\n\nThen add '---COMMUNITY REACTION---' on a new line, followed by 2-3 sentences capturing the TONE and SENTIMENT of the comments. Are people excited, skeptical, confused, grateful? What emotions come through? What are they debating or praising? Write with personality—this should feel like reading the comments section, not a dry summary.";
    }
}
