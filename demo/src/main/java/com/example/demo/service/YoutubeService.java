package com.example.demo.service;

import com.example.demo.dto.TranscriptData;
import com.example.demo.dto.YoutubeApiResponse;
import com.example.demo.dto.YoutubeData;
import io.github.thoroldvix.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class YoutubeService {

    @Value("${youtube.api}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private YoutubeTranscriptApi youtubeTranscriptApi;

    public YoutubeData getData(String videoId){
        String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet,contentDetails&id=" +videoId + "&key=" + apiKey;
        YoutubeApiResponse response = restTemplate.getForObject(url, YoutubeApiResponse.class);
        YoutubeApiResponse.Item item = response.getItems().get(0);
        YoutubeData youtubeData = new YoutubeData();
        youtubeData.setChannel(item.getSnippet().getChannelTitle());
        youtubeData.setTitle(item.getSnippet().getTitle());
        youtubeData.setDuration(item.getContentDetails().getDuration());
        return youtubeData;
    }

    public TranscriptData getTranscript(String videoId) throws TranscriptRetrievalException {

        TranscriptList transcriptList = youtubeTranscriptApi.listTranscripts(videoId);
        Transcript transcript = transcriptList.findTranscript("en");
        TranscriptData transcriptData = new TranscriptData();
        transcriptData.setVideoId(videoId);
        TranscriptContent content = transcript.fetch();
        List<TranscriptContent.Fragment> fragments = content.getContent();
        String finalBuild = fragments.stream()
                .map(TranscriptContent.Fragment::getText)
                .reduce("", (a, b) -> a + " " + b);
        transcriptData.setTranscript(finalBuild);
        return transcriptData;
    }

}
