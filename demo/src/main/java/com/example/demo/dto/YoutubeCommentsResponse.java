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
    }

    @Data
    public static class CommentThread {
        private String kind;
        private String etag;
        private String id;
        private ThreadSnippet snippet;   // thread-level snippet
        private Replies replies;         // replies to the top-level comment
    }

    /**
     * Thread-level snippet (for each item in commentThreads).
     * Contains the top-level comment and metadata about the thread.
     */
    @Data
    public static class ThreadSnippet {
        private String channelId;
        private String videoId;
        private Comment topLevelComment; // main comment in the thread
        private boolean canReply;
        private int totalReplyCount;
        private boolean isPublic;
    }

    @Data
    public static class Replies {
        private List<Comment> comments;
    }

    /**
     * Represents a comment (top-level or reply).
     */
    @Data
    public static class Comment {
        private String kind;
        private String etag;
        private String id;
        private CommentSnippet snippet; // comment-level snippet
    }

    /**
     * Comment-level snippet (for each Comment, including replies).
     */
    @Data
    public static class CommentSnippet {
        private String channelId;
        private String videoId;
        private String textDisplay;
        private String textOriginal;
        private String parentId;              // null for top-level comments
        private String authorDisplayName;
        private String authorProfileImageUrl;
        private String authorChannelUrl;
        private AuthorChannelId authorChannelId;
        private boolean canRate;
        private String viewerRating;
        private int likeCount;
        private String publishedAt;
        private String updatedAt;
    }

    @Data
    public static class AuthorChannelId {
        private String value;
    }
}