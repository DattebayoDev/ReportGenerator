# Report Generator - Progress Tracker

*Rolling 2-week window. Older weeks will be archived to HISTORY.md.*

---

## Week of Dec 29, 2025 - Jan 4, 2026: Railway Deployment Sprint

**Status:** The YouTube MVP is complete and tested locally. Ready for deployment.

**Deployment Blocks:**
- ~~**Block 1: Externalize API Keys**~~ ✓ Complete
- ~~**Block 2: Railway Setup**~~ ✓ Complete
- ~~**Block 3: Configure Railway Environment**~~ ✓ Complete
- ~~**Block 4: First Deployment**~~ ✓ Complete
- ~~**Block 5: Test Deployed App**~~ ✓ Complete
- **Block 6: Debug Duplicate Video Bug** ← Next
- **Block 7: Fix JSON Response Formatting**
- **Block 8: Other Issues (Buffer)**
- **Block 9: Production Usage**

<details>
<summary><b>Block Details</b></summary>

**Block 1: Externalize API Keys** ✓
Move youtube.api and OPENAI_API_KEY from application.properties to environment variables. Update LlmService to use proper environment variable naming conventions. Test locally with environment variables set to make sure everything still works. **Goal:** The app should run locally using OS environment variables instead of hardcoded values.

**Block 2: Railway Setup**
Create a Railway account and start a new project for Report Generator. Connect the GitHub repository to Railway so deployments can happen automatically. **Goal:** Railway project is configured and linked to the repo.

**Block 3: Configure Railway Environment**
Add the YOUTUBE_API environment variable in the Railway dashboard. Add the OPENAI_API_KEY environment variable as well. Configure the H2 file persistence path to work with Railway's filesystem. **Goal:** All secrets and configuration are properly set up in Railway.

**Block 4: First Deployment**
Deploy to Railway and monitor the build logs for errors. Verify that the Spring Boot application starts successfully in the Railway environment. **Goal:** App is deployed and running on Railway.

**Block 5: Test Deployed App** ✓
POST a YouTube URL to the Railway endpoint and verify that transcript fetching works in production. Confirm that OpenAI summarization works in production and check that database persistence works across restarts. **Goal:** The full YouTube flow is working in production.

**Block 6: Debug Duplicate Video Bug**
Investigate why the first video POSTed works correctly, but subsequent videos return the original video's data instead of processing new ones. Trace through the request flow from UrlController → YoutubeService → database to identify where duplicate detection or caching is preventing new videos from being saved. Add logging if needed to see what's happening with each POST request. **Goal:** Every unique YouTube URL should be processed and saved correctly.

**Block 7: Fix JSON Response Formatting**
Figure out why JSON responses are pretty-printed in localhost but appear as one line in production. Research Spring Boot JSON formatting configuration and ensure production responses are human-readable. **Goal:** Production API responses should be formatted the same way as localhost.

**Block 8: Other Issues (Buffer)**
Debug any other deployment-specific errors that come up. Adjust Railway configuration as needed and handle any environment-specific bugs that only appear in production. **Goal:** A stable production deployment that reliably works.

**Block 9: Production Usage**
Start using the deployed app for real YouTube videos that I actually want to watch. Collect feedback on summary quality to see if the prompts need improvement. Document any issues or ideas for improvements. Plan what comes next whether that's Reddit API integration, better prompts, or maybe even a UI. **Goal:** Actually using the product I built instead of just building it.

</details>

---

<details>
<summary><b>Session Logs</b></summary>

#### Monday Dec 30 (45 minutes)
**What was planned:** Complete Block 1 by testing the app locally with environment variables to confirm everything still works after externalizing API keys.

**What actually happened:** Fixed multiple configuration issues to get the app deployment-ready. Discovered that Lombok annotations weren't working with Maven CLI builds even though IntelliJ compiled fine. Added maven-compiler-plugin configuration with annotationProcessorPaths to enable Lombok annotation processing during Maven compilation. Cleaned up Report and Transcript entities by removing all manual getters and setters, replacing them with @Getter and @Setter annotations. Fixed "table report not found" errors by adding spring.jpa.hibernate.ddl-auto=update to application.properties, which tells Hibernate to automatically generate tables from entity classes. Tested the full app locally with environment variables and confirmed that transcript fetching, OpenAI summarization, and database persistence all work correctly. Block 1 is now complete.

**What didn't finish:** Railway deployment hasn't started yet. Need to create a Railway account and configure the project (Block 2).

**Next session:** Move to Block 2 and set up Railway. Create an account, start a new project, and connect the GitHub repository for automatic deployments.

#### Tuesday Dec 31 (45 minutes)
**What was planned:** Complete Block 2 by setting up Railway. Create an account, start a new project, and connect the GitHub repository for automatic deployments.

**What actually happened:** Downgraded from Java 24 to Java 17 to resolve Railway deployment compatibility issues. Discovered that Java 17 doesn't support the List.getFirst() method, which was added in Java 21 as part of the Sequenced Collections feature. Replaced getFirst() calls with get(0) in LlmService.java to maintain Java 17 compatibility. Updated pom.xml to target Java 17 and configured IntelliJ to use the correct JDK. Completed Railway account setup and connected the GitHub repository to the Railway project for automatic deployments. Block 2 is now complete.

**What didn't finish:** Railway environment variables haven't been configured yet. Need to add YOUTUBE_API and OPENAI_API_KEY to the Railway dashboard (Block 3).

**Next session:** Move to Block 3 and configure Railway environment variables. Add the API keys to Railway dashboard and verify the deployment configuration is ready for the first deployment attempt.

</details>

---

## Past Weeks

<details>
<summary><b>Week of Dec 22-28, 2025</b></summary>

#### Monday Dec 22 (Session time not tracked)
**What was planned:** Start implementing getTranscript() method to fetch actual YouTube transcripts.

**What actually happened:** Designed the Transcript entity with a one-to-one relationship to Report using @JoinColumn. Created TranscriptRepository with a findByVideoId method and built the TranscriptData DTO. Spent time learning about JPA relationships, foreign keys, and when it makes sense to decouple service methods. Debugged some intentional bugs including a missing setter and wrong parameter type.

**What didn't finish:** Still need to implement the getTranscript() method in YoutubeService since I haven't decided on the library or HTTP client approach yet. Transcripts aren't being stored in the Transcript entity, and there's no handling for videos without captions.

#### Tuesday Dec 23 (Session time not tracked)
**What was planned:** Build a keyword-based sentiment analyzer with positive/negative word lists to score transcripts.

**What actually happened:** Added the youtube-transcript-api dependency to pom.xml and started implementing getTranscript() in YoutubeService. Discovered an architectural decision point where videos can have multiple transcripts with varying quality. Learned the distinction between listTranscripts() which gives metadata versus getTranscript() which fetches actual content. Set up the session end protocol in CLAUDE.md for a consistent workflow and identified/fixed a contradiction in the bug fixing policy.

**What didn't finish:** Haven't implemented the transcript selection logic to decide which language or quality level to prioritize. The getTranscript() method isn't complete, transcripts aren't being stored in the entity yet, and I haven't tested with real YouTube videos.

#### Wednesday Dec 24 - Thursday Dec 25
*Christmas break - no coding*

#### Friday Dec 26 (55 minutes: 40 + 15)
**What was planned:** Replace the mock RedditService with real API calls.

**What actually happened:** Completed the getTranscript() implementation in YoutubeService. It now fetches English transcripts using findTranscript("en") and combines all transcript fragments into a single text using streams. Updated CLAUDE.md with the "build fundamentals first" learning principle. Configured the YoutubeTranscriptApi bean using TranscriptApiFactory.createDefault() and removed the unnecessary YoutubeClient dependency. Tested transcript fetching with a real YouTube video (Rick Astley) and it works. Clarified that transcript fetching should happen in the /analyze flow and added a transcript field to the TranscriptData DTO. Wired transcript fetching into the /analyze endpoint for YouTube only.

**What didn't finish:** Still need to link Transcript to Report (missing transcript.setReport(report)). Reddit API work has been deferred.

#### Saturday Dec 27 (60 minutes)
**What was planned:** Fix the Transcript-Report relationship by adding the missing transcript.setReport(report) call and test the full /analyze flow.

**What actually happened:** Discovered a Jackson version incompatibility between Spring Boot 4.0 (which uses Jackson 2.18) and the theokanning openai-gpt3-java library (which uses Jackson 2.13). Found out that theokanning/openai-gpt3-java is archived and unmaintained. Researched dependency conflict resolution strategies, weighing whether to downgrade the framework or replace the library. Learned about "blast radius of change" and the benefits of loose coupling. Swapped to the official com.openai:openai-java library (version 4.13.0) and updated LlmService to use the new OpenAI SDK. Discovered the distinction between environment variables and application.properties.

**What didn't finish:** OpenAI client bean configuration isn't complete. LlmService.summarize() isn't wired into the /analyze endpoint yet, so summaries aren't actually being used. Haven't tested the full transcript summarization flow. The Transcript-Report relationship still needs fixing.

#### Sunday Dec 28 (75 minutes)
**What was planned:** Complete the OpenAI integration, wire LlmService.summarize() into the /analyze endpoint, and test the full transcript summarization flow.

**What actually happened:** Session crashed mid-work but recovered context successfully. Confirmed that the MVP is FULLY WORKING for YouTube with real transcript fetching, OpenAI GPT-4 summarization, file-based H2 persistence, and a working Report-Transcript one-to-one relationship. Restored the deleted Reddit files to preserve mock code for later. Identified several knowledge gaps during code review including why save order matters for JPA entities with foreign keys, transaction rollback behavior on exceptions, the difference between @Value and System.getenv() usage patterns, and the difference between OpenAI's Responses API and Chat Completions API. Planned the deployment sprint for next week targeting Railway.

**What didn't finish:** API keys are still hardcoded in application.properties and need to be externalized to environment variables. Railway deployment hasn't started yet. Production testing hasn't happened.

</details>
