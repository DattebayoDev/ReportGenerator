# Report Generator - Progress Tracker

*Rolling 2-week window. Older weeks will be archived to HISTORY.md.*

---

## Week of Dec 22-28, 2025

### Monday Dec 22 (Session time not tracked)
**What was planned:** Start implementing getTranscript() method to fetch actual YouTube transcripts.

**What actually happened:** Designed the Transcript entity with a one-to-one relationship to Report using @JoinColumn. Created TranscriptRepository with a findByVideoId method and built the TranscriptData DTO. Spent time learning about JPA relationships, foreign keys, and when it makes sense to decouple service methods. Debugged some intentional bugs including a missing setter and wrong parameter type.

**What didn't finish:** Still need to implement the getTranscript() method in YoutubeService since I haven't decided on the library or HTTP client approach yet. Transcripts aren't being stored in the Transcript entity, and there's no handling for videos without captions.

### Tuesday Dec 23 (Session time not tracked)
**What was planned:** Build a keyword-based sentiment analyzer with positive/negative word lists to score transcripts.

**What actually happened:** Added the youtube-transcript-api dependency to pom.xml and started implementing getTranscript() in YoutubeService. Discovered an architectural decision point where videos can have multiple transcripts with varying quality. Learned the distinction between listTranscripts() which gives metadata versus getTranscript() which fetches actual content. Set up the session end protocol in CLAUDE.md for a consistent workflow and identified/fixed a contradiction in the bug fixing policy.

**What didn't finish:** Haven't implemented the transcript selection logic to decide which language or quality level to prioritize. The getTranscript() method isn't complete, transcripts aren't being stored in the entity yet, and I haven't tested with real YouTube videos.

### Wednesday Dec 24 - Thursday Dec 25
*Christmas break - no coding*

### Friday Dec 26 (55 minutes: 40 + 15)
**What was planned:** Replace the mock RedditService with real API calls.

**What actually happened:** Completed the getTranscript() implementation in YoutubeService. It now fetches English transcripts using findTranscript("en") and combines all transcript fragments into a single text using streams. Updated CLAUDE.md with the "build fundamentals first" learning principle. Configured the YoutubeTranscriptApi bean using TranscriptApiFactory.createDefault() and removed the unnecessary YoutubeClient dependency. Tested transcript fetching with a real YouTube video (Rick Astley) and it works. Clarified that transcript fetching should happen in the /analyze flow and added a transcript field to the TranscriptData DTO. Wired transcript fetching into the /analyze endpoint for YouTube only.

**What didn't finish:** Still need to link Transcript to Report (missing transcript.setReport(report)). Reddit API work has been deferred.

### Saturday Dec 27 (60 minutes)
**What was planned:** Fix the Transcript-Report relationship by adding the missing transcript.setReport(report) call and test the full /analyze flow.

**What actually happened:** Discovered a Jackson version incompatibility between Spring Boot 4.0 (which uses Jackson 2.18) and the theokanning openai-gpt3-java library (which uses Jackson 2.13). Found out that theokanning/openai-gpt3-java is archived and unmaintained. Researched dependency conflict resolution strategies, weighing whether to downgrade the framework or replace the library. Learned about "blast radius of change" and the benefits of loose coupling. Swapped to the official com.openai:openai-java library (version 4.13.0) and updated LlmService to use the new OpenAI SDK. Discovered the distinction between environment variables and application.properties.

**What didn't finish:** OpenAI client bean configuration isn't complete. LlmService.summarize() isn't wired into the /analyze endpoint yet, so summaries aren't actually being used. Haven't tested the full transcript summarization flow. The Transcript-Report relationship still needs fixing.

### Sunday Dec 28 (75 minutes)
**What was planned:** Complete the OpenAI integration, wire LlmService.summarize() into the /analyze endpoint, and test the full transcript summarization flow.

**What actually happened:** Session crashed mid-work but recovered context successfully. Confirmed that the MVP is FULLY WORKING for YouTube with real transcript fetching, OpenAI GPT-4 summarization, file-based H2 persistence, and a working Report-Transcript one-to-one relationship. Restored the deleted Reddit files to preserve mock code for later. Identified several knowledge gaps during code review including why save order matters for JPA entities with foreign keys, transaction rollback behavior on exceptions, the difference between @Value and System.getenv() usage patterns, and the difference between OpenAI's Responses API and Chat Completions API. Planned the deployment sprint for next week targeting Railway.

**What didn't finish:** API keys are still hardcoded in application.properties and need to be externalized to environment variables. Railway deployment hasn't started yet. Production testing hasn't happened.

---

## Week of Dec 29, 2025 - Jan 4, 2026: Railway Deployment Sprint

**Status heading into this week:** The YouTube MVP is complete and tested locally. It's ready for deployment.

### Block 1: Externalize API Keys
Move youtube.api and OPENAI_API_KEY from application.properties to environment variables. Update LlmService to use proper environment variable naming conventions. Test locally with environment variables set to make sure everything still works. **Goal:** The app should run locally using OS environment variables instead of hardcoded values.

### Block 2: Railway Setup
Create a Railway account and start a new project for Report Generator. Connect the GitHub repository to Railway so deployments can happen automatically. **Goal:** Railway project is configured and linked to the repo.

### Block 3: Configure Railway Environment
Add the YOUTUBE_API environment variable in the Railway dashboard. Add the OPENAI_API_KEY environment variable as well. Configure the H2 file persistence path to work with Railway's filesystem. **Goal:** All secrets and configuration are properly set up in Railway.

### Block 4: First Deployment
Deploy to Railway and monitor the build logs for errors. Verify that the Spring Boot application starts successfully in the Railway environment. **Goal:** App is deployed and running on Railway.

### Block 5: Test Deployed App
POST a YouTube URL to the Railway endpoint and verify that transcript fetching works in production. Confirm that OpenAI summarization works in production and check that database persistence works across restarts. **Goal:** The full YouTube flow is working in production.

### Block 6: Fix Issues (Buffer)
Debug any deployment-specific errors that come up. Adjust Railway configuration as needed and handle any environment-specific bugs that only appear in production. **Goal:** A stable production deployment that reliably works.

### Block 7: Production Usage
Start using the deployed app for real YouTube videos that I actually want to watch. Collect feedback on summary quality to see if the prompts need improvement. Document any issues or ideas for improvements. Plan what comes next whether that's Reddit API integration, better prompts, or maybe even a UI. **Goal:** Actually using the product I built instead of just building it.