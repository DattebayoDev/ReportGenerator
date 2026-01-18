package com.example.demo.service;

import com.example.demo.dto.TranscriptData;
import com.example.demo.dto.YoutubeCommentsResponse;
import io.github.thoroldvix.api.*;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class YoutubeService {

    @Value("${YOUTUBE_API}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private YoutubeTranscriptApi youtubeTranscriptApi;

    @Timed(value = "youtube.transcript.fetch", description = "Time taken to fetch YouTube transcript")
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

    @Timed(value = "youtube.comments.fetch", description = "Time taken to fetch YouTube comments")
    public List<String> getComments(String videoId) {
        String url = "https://www.googleapis.com/youtube/v3/commentThreads?part=snippet&videoId="
                + videoId + "&maxResults=100&order=relevance&key=" + apiKey;

        YoutubeCommentsResponse response = restTemplate.getForObject(url, YoutubeCommentsResponse.class);

        if (response == null || response.getItems() == null) {
            return List.of();
        }

        // Get all top-level comments
        var topLevelComments = response.getItems().stream()
                .map(item -> item.getSnippet().getTopLevelComment().getSnippet().getTextOriginal());

        // Get all reply comments
        var replyComments = response.getItems().stream()
                .filter(item -> item.getReplies() != null)
                .flatMap(item -> item.getReplies().getComments().stream())
                .map(comment -> comment.getSnippet().getTextOriginal());

        // Combine both streams and return as list
        return java.util.stream.Stream.concat(topLevelComments, replyComments)
                .toList();
    }

}
