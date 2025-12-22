# Week of Dec 22-28, 2025

## ✅ Completed
- [x] REST endpoints (GET /reports, GET /reports/{id}, DELETE /reports/{id})
- [x] Platform filtering (GET /reports/platform?website=youtube)
- [x] YouTube API integration (fetch real video metadata)
- [x] Transcript entity and repository design
- [x] TranscriptData DTO creation

---

## 📋 This Week

### Monday Dec 22
- [x] Design Transcript entity with one-to-one relationship to Report
- [x] Create TranscriptRepository with findByVideoId method
- [ ] Implement getTranscript() method in YoutubeService
- [ ] Store transcripts in Transcript entity
- [ ] Handle videos without captions (error handling)

### Tuesday Dec 23
- [ ] Build keyword-based sentiment analyzer
- [ ] Create positive/negative word lists
- [ ] Score transcripts based on keyword matching

### Wednesday Dec 24
- [ ] Add sentiment score to Report entity
- [ ] Update report summary to include sentiment
- [ ] Test with different video types

### Thursday Dec 25
- [ ] Research Reddit API authentication
- [ ] Get Reddit API credentials
- [ ] Build RedditApiClient similar to YouTube

### Friday Dec 26
- [ ] Replace mock RedditService with real API calls
- [ ] Fetch Reddit post data (title, description, upvotes)
- [ ] Update controller to use real Reddit data

### Weekend (Dec 27-28)
- [ ] Add error handling for API failures
- [ ] Add logging for debugging
- [ ] Manual testing with various URLs

---

## 🎯 Current Focus
**Monday Dec 22:** YouTube transcript fetching
