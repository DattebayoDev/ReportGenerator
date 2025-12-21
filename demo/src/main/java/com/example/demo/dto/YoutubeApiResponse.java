package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class YoutubeApiResponse {
    private List<Item> items;

    @Data
    public static class Item {
        private Snippet snippet;
        private ContentDetails contentDetails;
    }

    @Data
    public static class Snippet {
        private String title;
        private String channelTitle;
    }

    @Data
    public static class ContentDetails {
        private String duration;
    }
}