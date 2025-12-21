# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Run Commands

All commands should be run from the `demo/` directory.

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ReportGeneratorApplicationTests

# Run a single test method
./mvnw test -Dtest=ReportGeneratorApplicationTests#contextLoads
```

On Windows, use `mvnw.cmd` instead of `./mvnw`.

---

## Your Profile as a Programmer

**Learning Style:**
* Impatient - wants SHORT, DIRECT answers
* Hates when given full solutions immediately - wants guided discovery with hints
* Struggles with analysis paralysis - overthinks and researches instead of building
* Needs to feel progress through small, finished wins (30-60 min sessions)
* Has abandoned projects when life interrupts - working on consistency
* Benefits from firm guidance to prevent scope creep and overplanning

**Current Situation:**
* 24yo junior backend Java developer (started Aug 2025, ~4 months in)
* Uses Java/Spring Boot at work but copy-pastes without understanding
* Never finished a coding project start-to-finish before
* Has finance background, wants to become principal engineer by 30

**Communication Preferences:**
* Be a mentor, not a solution-giver
* Ask diagnostic questions instead of giving answers
* Call out when showing analysis paralysis tendencies
* Force action over planning ("what should I BUILD today?" not "what should I learn?")
* ONE question at a time, no lengthy explanations
* Do NOT give answers immediately - give hints and let me figure it out

---

## Current Project: Reddit/YouTube Report Generator

**The Idea:**
API that accepts Reddit/YouTube links and generates concise reports based on content. Saves time by summarizing whether content is worth consuming.

**Why This Project:**
* Solves YOUR actual problem (wasting time on videos/threads)
* More exciting than generic CRUD apps
* Can build incrementally with daily 30-min wins
* Eventually will integrate real APIs (YouTube transcription, Reddit API)

**Tech Stack:**
* Spring Boot 4.0.0 with Java 25
* IntelliJ configured to delegate builds to Maven (for Java 25 compatibility)
* Lombok for boilerplate reduction
* H2 in-memory database (for now)
* Spring JPA for database operations

---

## Current Project Status (as of Dec 15, 2025)

### ✅ Completed Features

**Day 1-2: Basic Infrastructure**
* POST /analyze - accepts URL in request body
* GET /analyze - returns all saved URLs
* Entity: `UrlRequest` with URL field
* Repository: `UrlRepository` extends JpaRepository

**Day 3: URL Detection**
* Service: `UrlDetector`
* Method: `detectPlatform(String url)` returns "YOUTUBE", "REDDIT", or "UNKNOWN"
* Checks for "youtube", "youtu.be", or "reddit" in URL string

**Day 4: Mock Services**
* DTOs: `YoutubeData` (title, channel, duration), `RedditData` (title, description, upvote)
* Services: `YoutubeService.getData()`, `RedditService.getData()` return hardcoded mock data
* Controller returns appropriate mock data based on platform

**Sunday Dec 14: Report Generation**
* Service: `ReportGenerator` with methods:
    - `generateYoutubeReport(YoutubeData)` - returns summary string
    - `generateRedditReport(RedditData)` - returns summary string
* Controller now returns generated report strings instead of raw data
* Hardcoded summaries using string concatenation (no AI yet)

**Monday Dec 15: Caching System**
* Entity: `Report` with fields: id, url, platform, summary, timestamp
* Repository: `ReportRepository` with custom method `findByUrl(String url)`
* Flow: Check database before generating → return cached if exists → save new reports
* Removed redundant `UrlRepository` (Report contains everything UrlRequest had)

**Tuesday Dec 16: URL Normalization**
* Service: `UrlParser` with methods:
    - `parseYoutubeUrl(String url)` - extracts video ID from both youtube.com and youtu.be formats
    - `parseRedditUrl(String url)` - extracts post ID
* Database now stores extracted IDs instead of full URLs
* Deduplication working: same video with different URL formats = one cached report

---

## Current Code Structure

```
src/main/java/com/example/demo/
├── controller/
│   └── UrlController.java          # POST /analyze, GET /analyze
├── dto/
│   ├── YoutubeData.java            # title, channel, duration
│   └── RedditData.java             # title, description, upvote
├── entity/
│   └── Report.java                 # id, url, platform, summary, timestamp
├── repository/
│   └── ReportRepository.java       # findByUrl(String url)
├── service/
│   ├── UrlDetector.java            # detectPlatform(String url)
│   ├── UrlParser.java              # parseYoutubeUrl(), parseRedditUrl()
│   ├── YoutubeService.java         # getData() - returns mock data
│   ├── RedditService.java          # getData() - returns mock data
│   └── ReportGenerator.java        # generateYoutubeReport(), generateRedditReport()
```

---

## Week Plan: Dec 15-21, 2025

**✅ Sunday Dec 14 (DONE):**
- Reddit mock service
- ReportGenerator service
- Controller returns reports

**✅ Monday Dec 15 (DONE):**
- Report entity with caching
- Check database before generating
- Save new reports

**✅ Tuesday Dec 16 (DONE):**
- URL parsing for YouTube (both formats)
- URL parsing for Reddit
- Store normalized IDs

**Wednesday Dec 17:**
- Add REST endpoints: GET /reports, GET /reports/{id}, DELETE /reports/{id}
- Add filtering: GET /reports?platform=YOUTUBE

**Thursday Dec 18:**
- Get YouTube API key
- Build YouTubeApiClient that fetches real metadata
- Replace mock YouTube service with real API calls

**Friday Dec 19:**
- Fetch YouTube transcripts (use library or HTTP client)
- Store transcripts in database
- Handle videos without captions

**Saturday Dec 20:**
- Build keyword-based sentiment analyzer (no AI)
- Score transcripts: positive words vs negative words
- Add sentiment to generated reports

---

## Key Patterns & Decisions Made

1. **Three-layer architecture:** Controller → Service → Repository
2. **Mock-first approach:** Build with fake data, swap in real APIs later
3. **Single responsibility:** Each service has ONE job
4. **Caching before features:** Optimize early to avoid redundant work
5. **URL normalization:** Store IDs, not raw URLs for deduplication
6. **Lombok usage:** @Data for DTOs, @Entity for database tables, @Service/@RestController for Spring components
I th