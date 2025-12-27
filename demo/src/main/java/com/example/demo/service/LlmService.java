package com.example.demo.service;

import com.theokanning.openai.assistants.AssistantRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.messages.MessageRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LlmService {

    @Autowired
    private OpenAiService openAiService;

    public String summarize(String Transcript) {
        String prompt = "Summarize the following transcript in a concise manner:\n\n" + Transcript;
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRole("system");
        chatMessage.setContent(prompt);

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel("gpt-4o");
        chatCompletionRequest.setMessages(List.of(chatMessage));

        ChatCompletionResult result = openAiService.createChatCompletion(chatCompletionRequest);
        return result.getChoices().getFirst().getMessage().getContent().trim();

    }
}
