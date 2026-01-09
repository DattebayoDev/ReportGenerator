package com.example.demo;

import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientImpl;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import io.github.thoroldvix.api.TranscriptApiFactory;
import io.github.thoroldvix.api.YoutubeTranscriptApi;
import io.github.thoroldvix.api.YoutubeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class ReportGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportGeneratorApplication.class, args);
	}

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    private YoutubeClient defaultYoutubeClient;

    @Bean
    public YoutubeTranscriptApi youtubeTranscriptApi() {
        return TranscriptApiFactory.createDefault();
    }


}
