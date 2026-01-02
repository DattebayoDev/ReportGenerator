# Report Generator - Progress Tracker

*Rolling 2-week window. Older weeks will be archived to HISTORY.md.*

---

## Week of Dec 30, 2025 - Jan 5, 2026: Web UI Sprint

**Status:** Basic web UI working - can submit URLs, select archetype mode, and view summaries. Need to polish UX and add reports history.

**Web UI Blocks:**
- **Block 1: HTML form with URL input and archetype selection** ✓
- **Block 2: Wire form to /analyze endpoint and display summary** ✓
- **Block 3: Fix View Reports button and build reports history page** ← Next
- **Block 4: Deploy updated UI to Railway and test in production**

**Future Work:**
- Add loading indicator/spinner for LLM analysis
- Handle videos without transcripts (error message/fallback)
- Source and analyze YouTube/Reddit comments

<details>
<summary><b>Block Details</b></summary>

**Block 1: HTML form with URL input and archetype selection** ✓
Created index.html with URL input field, archetype radio buttons (TWELVE_YEAR_OLD, ENTRY_LEVEL, HIGH_LEVEL, CUSTOM), and custom prompt textarea that shows/hides based on selection. Added basic CSS styling. Configured Spring Boot to serve static files from resources/static. Added View Reports button (currently broken). **Completed:** Dec 31 sessions.

**Block 2: Wire form to /analyze endpoint and display summary** ✓
Implemented JavaScript fetch() call to POST URL and archetype to /analyze endpoint. Backend accepts AnalysisRequest DTO with url, archetype, and customPrompt fields. Summary response displays on page after analysis completes. Fixed caching bug by adding archetype to Report entity and updating lookup logic to check both postId and archetype (findByPostIdAndArchetype). Tested with multiple videos and different archetypes - works correctly. **Completed:** Dec 31 and Jan 1 sessions.

**Block 3: Fix View Reports button and build reports history page**
Debug View Reports button navigation issue. Create reports.html page that fetches GET /reports and displays all previously analyzed videos in a table/list format. Show postId, platform, archetype, timestamp, and summary preview. Add ability to click a report to see full details. Consider adding filtering by platform or archetype. **Goal:** Users can review past analyses without re-processing URLs.

**Block 4: Deploy updated UI to Railway and test in production**
Push HTML/CSS/JS changes to GitHub and verify Railway auto-deploys correctly. Test full flow in production with real YouTube videos. Use the app for at least 3 videos to validate it solves the original problem of deciding whether content is worth consuming. Document bugs or UX improvements discovered during real usage. **Goal:** Daily-usable web app deployed and tested with real content.

</details>

---

## Session History

<details>
<summary><b>Thursday Jan 1 - Session 2 (20 minutes)</b></summary>

**What was planned:** Fix the caching bug by modifying the lookup logic in UrlController to consider both postId and archetype when checking if a report already exists, adding archetype field to the Report entity and updating the repository findBy methods.

**What actually happened:** Started by discussing database migration strategies and the implications of adding a new column to an existing table with data. Learned about the distinction between side projects with test data versus production systems, and when it's acceptable to let ddl-auto handle schema changes versus using explicit migration tools like Flyway or Liquibase. Fixed the caching bug by adding an archetype field to the Report entity with the @Enumerated annotation set to EnumType.STRING to store the enum name rather than its ordinal position. Created a new findByPostIdAndArchetype method in ReportRepository that generates a composite key lookup using Spring Data JPA's method naming convention. Updated UrlController to check both postId and archetype in the lookup logic on line 54, and modified the report creation logic to set the archetype field for both YouTube and Reddit reports. Tested the fix and confirmed that analyzing the same video with different archetypes now generates fresh summaries instead of returning cached results. Discovered through testing that videos without transcripts cause errors, and identified future work items including adding a loading indicator for the UI and sourcing comments from YouTube and Reddit for more comprehensive analysis.

**What didn't finish:** The View Reports button navigation issue remains unresolved. Need to handle videos without transcripts gracefully with error messages or fallback behavior. Loading indicator for UI not implemented yet. Comment sourcing feature not started.

**Next session:** Fix the View Reports button and build the reports history page.

</details>

<details>
<summary><b>Thursday Jan 1 - Session 1 (45 minutes)</b></summary>

**What was planned:** Implement radio button UI for analysis modes and wire them through backend to LlmService to customize prompts.

**What actually happened:** Designed and implemented the full archetype feature end-to-end. Started by discussing the data contract design, deciding to create an AnalysisRequest DTO that combines URL and archetype fields rather than separate parameters, since Spring only allows one @RequestBody per endpoint. Created the AnalysisArchetype enum with four values (TWELVE_YEAR_OLD, ENTRY_LEVEL, HIGH_LEVEL, CUSTOM) and added it to a new enums package. Updated the frontend with radio buttons for archetype selection and a textarea that shows/hides when CUSTOM is selected. Modified app.js to send both archetype and customPrompt in the request body. On the backend, updated UrlController to accept AnalysisRequest instead of UrlRequest, and modified LlmService.summarize() to take archetype and customPrompt parameters. Created a private buildPrompt() method in LlmService using a switch expression to map each archetype to its specific prompt text, with fallback logic that uses ENTRY_LEVEL if CUSTOM is selected but customPrompt is empty. Tested the feature and confirmed it works - different archetypes produce different summaries. Discovered a caching bug where the lookup logic only checks postId, so re-analyzing the same video with a different archetype returns the cached summary instead of generating a new one. This will need fixing in a future session since the Report entity needs to track both postId and archetype for proper caching.

**What didn't finish:** The caching logic bug remains unfixed - same video with different archetypes should produce multiple summaries but currently returns the first cached result. The View Reports button navigation issue is still unresolved.

**Next session:** Fix the caching bug by modifying the lookup logic in UrlController to consider both postId and archetype when checking if a report already exists. May need to add archetype field to the Report entity and update the repository findBy methods.

</details>

<details>
<summary><b>Wednesday Dec 31 - Session 2 (60 minutes)</b></summary>

**What was planned:** Wire the Analyze button to the /analyze endpoint using the fetch() API and debug the View Reports button behavior.

**What actually happened:** Fixed the application.properties configuration issue that was causing static files to require app restarts. Discovered the file had been deleted and recreated it with proper cache settings. Debugged JavaScript code that had Java-style variable declarations and async timing issues. Learned the difference between const requiring an initializer versus Java's declaration syntax. Fixed multiple JavaScript errors including trying to call textContent as a function when it's a property, understanding that fetch is asynchronous so code outside the then callback executes before the response arrives, and debugging string escaping issues when trying to split by newline characters. Discovered that JSON.stringify on a string escapes the newlines making them unsplittable. Got the basic analyze flow working where clicking the button sends a request to the backend and displays the summary on the page. Planned to add radio button analysis modes to customize the LLM prompt but decided to implement that myself in the next session.

**What didn't finish:** Block 2 is complete (form wired to API). Haven't implemented the radio button analysis modes yet for customizing prompts. The View Reports button bug still needs debugging.

**Next session:** Implement radio button UI for analysis modes and wire them through backend to LlmService to customize prompts. Fix the View Reports button navigation issue.

</details>

<details>
<summary><b>Wednesday Dec 31 - Session 1 (30 minutes)</b></summary>

**What was planned:** Begin Block 1 by creating the HTML form page with URL input and submit button. Set up Spring Boot to serve static files.

**What actually happened:** Created the frontend scaffolding with index.html, style.css, and app.js in the resources/static directory. Discussed the trade-offs between keeping frontend and backend in the same project vs separate repositories, concluding that same-project is simpler for basic HTML/CSS/JS since it avoids CORS configuration and multiple deployments. Added a "View Reports" button alongside the Analyze button to navigate to a future reports page. Configured spring.web.resources.static-locations to enable live reload of static files during development without restarting the application. Debugged a 404 error caused by the file path being relative to the working directory.

**What didn't finish:** Block 1 is complete but the View Reports button has a bug to debug (intentional). Haven't wired the form to the /analyze API yet.

**Next session:** Wire the Analyze button to the /analyze endpoint using the fetch() API. Debug the View Reports button behavior.

</details>

<details>
<summary><b>Tuesday Dec 30 (45 minutes)</b></summary>

**What was planned:** Debug the duplicate video bug (Block 6) and fix JSON response formatting (Block 7).

**What actually happened:** Discovered that the "duplicate video bug" was user error - was sending the same request body repeatedly instead of different URLs. Learned the distinction between request body and query parameters, understanding when to use each based on data complexity and special characters. Investigated JSON serialization and confirmed the API is working correctly - responses are proper JSON but appear minified in browsers without extensions. Discussed API design patterns including idempotency, REST conventions, and why POST with request body is appropriate for the /analyze endpoint. Identified that the real blocker to using the product is lack of UI, not bugs. Planned the Web UI Sprint with 5 specific blocks focused on building a usable interface.

**What didn't finish:** Blocks 6 and 7 are complete (no actual bugs found). Haven't started UI development yet.

**Next session:** Begin Block 1 by creating the HTML form page with URL input and submit button. Set up Spring Boot to serve static files.

</details>

---

## Past Weeks

<details>
<summary><b>Week of Dec 29, 2025 - Jan 4, 2026: Railway Deployment Sprint (COMPLETE)</b></summary>

<details>
<summary><b>Monday Dec 29 (90 minutes total)</b></summary>

#### Session 1 (45 minutes)
**What was planned:** Complete Block 1 by testing the app locally with environment variables to confirm everything still works after externalizing API keys.

**What actually happened:** Fixed multiple configuration issues to get the app deployment-ready. Discovered that Lombok annotations weren't working with Maven CLI builds even though IntelliJ compiled fine. Added maven-compiler-plugin configuration with annotationProcessorPaths to enable Lombok annotation processing during Maven compilation. Cleaned up Report and Transcript entities by removing all manual getters and setters, replacing them with @Getter and @Setter annotations. Fixed "table report not found" errors by adding spring.jpa.hibernate.ddl-auto=update to application.properties, which tells Hibernate to automatically generate tables from entity classes. Tested the full app locally with environment variables and confirmed that transcript fetching, OpenAI summarization, and database persistence all work correctly. Block 1 is now complete.

**What didn't finish:** Railway deployment hasn't started yet. Need to create a Railway account and configure the project (Block 2).

**Next session:** Move to Block 2 and set up Railway. Create an account, start a new project, and connect the GitHub repository for automatic deployments.

#### Session 2 (45 minutes)
**What was planned:** Complete Block 2 by setting up Railway. Create an account, start a new project, and connect the GitHub repository for automatic deployments.

**What actually happened:** Downgraded from Java 24 to Java 17 to resolve Railway deployment compatibility issues. Discovered that Java 17 doesn't support the List.getFirst() method, which was added in Java 21 as part of the Sequenced Collections feature. Replaced getFirst() calls with get(0) in LlmService.java to maintain Java 17 compatibility. Updated pom.xml to target Java 17 and configured IntelliJ to use the correct JDK. Completed Railway account setup and connected the GitHub repository to the Railway project for automatic deployments. Completed Blocks 2, 3, 4, and 5 (Railway setup, environment configuration, first deployment, and testing).

**What didn't finish:** First video POSTed works but subsequent videos return the original video's data. JSON responses appear as one line in production instead of pretty-printed. Need to debug both issues.

**Next session:** Debug the duplicate video bug (Block 6) and fix JSON response formatting (Block 7).

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
