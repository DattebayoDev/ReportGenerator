package com.example.demo.service;

//import com.example.demo.dto.AnalysisResult;
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

    @Timed(value = "openai.summarize.content", description = "Time taken to generate content summary via OpenAI")
    public String summarizeContent(String transcript, AnalysisArchetype archetype, String customPrompt) {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();

        String prompt = buildContentPrompt(archetype, customPrompt);

        ResponseCreateParams params = ResponseCreateParams.builder()
        .input(prompt + "\n\nVIDEO TRANSCRIPT:\n" + transcript)
        .model(ChatModel.GPT_4_1)
        .build();

        Response response = client.responses().create(params);
        return response.output().get(0).message().get().content().get(0).asOutputText().text();
    }

    @Timed(value = "openai.summarize.comments", description = "Time taken to generate community reaction via OpenAI")
    public String summarizeComments(String comments) {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();

        String prompt = buildCommentsPrompt();

        ResponseCreateParams params = ResponseCreateParams.builder()
        .input(prompt + "\n\nCOMMUNITY COMMENTS:\n" + comments)
        .model(ChatModel.GPT_4_1)
        .build();

        Response response = client.responses().create(params);
        return response.output().get(0).message().get().content().get(0).asOutputText().text();
    }

    private String buildContentPrompt(AnalysisArchetype archetype, String customPrompt) {
        if (archetype == AnalysisArchetype.CUSTOM) {
            if (customPrompt != null && !customPrompt.isEmpty()) {
                return customPrompt;
            }
            // Fallback to KEY_POINTS if custom prompt is empty
            archetype = AnalysisArchetype.KEY_POINTS;
        }

        return switch (archetype) {
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
    }

    private String buildCommentsPrompt() {
        return """
                Write ONE SHORT PARAGRAPH (4-6 sentences max) that captures the VIBE and PULSE of the comment section. When someone reads this, they should instantly feel the energy and temperature of the audience.

                REQUIREMENTS:
                - ONE paragraph only, no line breaks
                - Focus on the dominant mood/tone first (hyped? arguing? grateful? confused?)
                - Include 1-2 specific examples (quotes, debates, or recurring jokes)
                - Show what people are agreeing on AND what they're fighting about
                - Use the audience's language and energy level
                - Skip generic observations - be specific and punchy

                BAD (too long and generic): "The comments section shows a mix of positive and negative reactions. Some viewers express appreciation while others have concerns. There are several debates happening and people are quoting specific moments from the video..."

                GOOD (punchy and specific): "People are absolutely losing it over the timestamp trick at 4:32—half the comments are just flame emojis. But there's a heated debate about whether method #2 actually works in production, with several developers saying it breaks. One top comment asks 'why didn't anyone teach this in school??' while others are demanding a follow-up video."
                """;
    }
}
