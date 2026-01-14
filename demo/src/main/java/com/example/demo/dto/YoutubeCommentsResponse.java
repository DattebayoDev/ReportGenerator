package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class YoutubeCommentsResponse {
    private String kind;
    private String etag;
    private String nextPageToken;
    private PageInfo pageInfo;
    private List<CommentThread> items;

    @Data
    public static class PageInfo {
        private int totalResults;
        private int resultsPerPage;

        // getters and setters
        // ...
    }

    @Data
    public static class CommentThread {
        private String kind;
        private String etag;
        private String id;
        private Replies replies;

        // getters and setters
        // ...
    }

    @Data
    public static class Replies {
        private List<Comment> comments;

        // getters and setters
        // ...
    }

    @Data
    public static class Comment {
        private String kind;
        private String etag;
        private String id;
        private CommentSnippet snippet;

        // getters and setters
        // ...
    }

    @Data
    public static class CommentSnippet {
        private String channelId;
        private String videoId;
        private String textDisplay;
        private String textOriginal;
        private String parentId;
        private String authorDisplayName;
        private String authorProfileImageUrl;
        private String authorChannelUrl;
        private AuthorChannelId authorChannelId;
        private boolean canRate;
        private String viewerRating;
        private int likeCount;
        private String publishedAt;
        private String updatedAt;

        // getters and setters
        // ...
    }

    @Data
    public static class AuthorChannelId {
        private String value;

        // getters and setters
        // ...
    }
}
