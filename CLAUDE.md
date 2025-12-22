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
* 80% thinking, 20% coding - prioritize deep understanding over quick implementation
* Ask substantial questions that require thoughtful responses, not single-line answers
* Avoid rapid back-and-forth - have real discussions about architecture, trade-offs, edge cases
* Call out when showing analysis paralysis tendencies, but encourage thinking through problems
* Do NOT give answers immediately - guide discovery through discussion
* **INTENTIONALLY include bugs/mistakes in generated code** - forces active debugging and critical thinking
* Code generation will be AI-commoditized; focus on building thinking/debugging skills instead
* Once bugs are discovered, automatically fix them - don't make user manually write the fix
* Keep discussions time-boxed - move to implementation before overthinking

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

**Wednesday Dec 17: REST Endpoints**
* (Skipped - moved to transcript work)

**Thursday Dec 18: YouTube API Integration**
* Got YouTube Data API v3 key
* Integrated real YouTube metadata fetching
* YoutubeService now calls live API instead of returning mock data

**Friday Dec 19: Transcript Database Design (IN PROGRESS)**
* Entity: `Transcript` with fields: id, videoId, transcriptText, report (one-to-one relationship)
* Repository: `TranscriptRepository` with `findByVideoId(String videoId)` method
* DTO: `TranscriptData` for transferring transcript data
* Architectural decisions:
    - Separated transcript storage from Report entity (transcripts can be large)
    - One-to-one relationship between Report and Transcript using @JoinColumn
    - Decoupled YoutubeService into getMetadata() and getTranscript() methods
    - Prevents unnecessary API calls by allowing independent fetching
* Next: Implement getTranscript() method to fetch actual YouTube transcripts

---

## Current Code Structure

```
src/main/java/com/example/demo/
├── controller/
│   └── UrlController.java          # POST /analyze, GET /analyze
├── dto/
│   ├── YoutubeData.java            # title, channel, duration
│   ├── RedditData.java             # title, description, upvote
│   └── TranscriptData.java         # videoId, transcript
├── entity/
│   ├── Report.java                 # id, postId, platform, summary, timestamp
│   └── Transcript.java             # id, videoId, transcriptText, report (1:1)
├── repository/
│   ├── ReportRepository.java       # findByUrl(String url)
│   └── TranscriptRepository.java   # findByVideoId(String videoId)
├── service/
│   ├── UrlDetector.java            # detectPlatform(String url)
│   ├── UrlParser.java              # parseYoutubeUrl(), parseRedditUrl()
│   ├── YoutubeService.java         # getMetadata() - fetches real YouTube data via API
│   ├── RedditService.java          # getData() - returns mock data
│   └── ReportGenerator.java        # generateYoutubeReport(), generateRedditReport()
```

---

## Key Patterns & Decisions Made

1. **Three-layer architecture:** Controller → Service → Repository
2. **Mock-first approach:** Build with fake data, swap in real APIs later
3. **Single responsibility:** Each service has ONE job
4. **Caching before features:** Optimize early to avoid redundant work
5. **URL normalization:** Store IDs, not raw URLs for deduplication
6. **Lombok usage:** @Data for DTOs, @Entity for database tables, @Service/@RestController for Spring components
I th