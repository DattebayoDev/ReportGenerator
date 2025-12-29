package com.example.demo.service;

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

    public String summarize(String transcript) {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();

        ResponseCreateParams params = ResponseCreateParams.builder()
        .input("Generate a brief summary of, 50 words" + transcript)
        .model(ChatModel.GPT_4_1)
        .build();

        Response response = client.responses().create(params);
        return response.output().get(0).message().get().content().get(0).asOutputText().text();
    }
}
