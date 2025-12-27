package com.example.demo.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LlmService {

//    @Autowired
//    private OpenAiService openAiService;

//    public String summarize(String Transcript) {
//        String prompt = "Summarize the following transcript in a concise manner:\n\n" + Transcript;
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setRole("system");
//        chatMessage.setContent(prompt);
//
//        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
//        chatCompletionRequest.setModel("gpt-4o");
//        chatCompletionRequest.setMessages(List.of(chatMessage));
//
//        ChatCompletionResult result = openAiService.createChatCompletion(chatCompletionRequest);
//        return result.getChoices().getFirst().getMessage().getContent().trim();
//
//    }

    @Value("${openai.api.key}")
    private String apiKey;

    public Response summarize(String Transcript) {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();

        ResponseCreateParams params = ResponseCreateParams.builder()
        .input("Top 5 basketball players")
        .model(ChatModel.GPT_4_1)
        .build();

        Response response = client.responses().create(params);
        return response;
    }
}
