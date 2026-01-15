# Report Generator - Progress Tracker

*Rolling 2-week window. Older weeks will be archived to HISTORY.md.*

---

## Blocks

**YouTube Comments Integration Sprint**

- **Block 1: Design service architecture and data structures** - Decide where comments logic lives (YoutubeService vs separate service), what data structure to use for comments (raw JSON vs DTO vs simple strings), and how comments flow through the system
- **Block 2: Implement YouTube Data API v3 comments fetching** - Create method to call commentThreads endpoint, handle API key from environment variables, fetch top 100 comments, parse response and extract items array
- **Block 3: Integrate comments into LLM analysis** - Modify LlmService to accept both transcript and comments, update prompt to include community reaction analysis, handle token limits (transcript + 100 comments could be large)
- **Block 4: Update UI to display Community Reaction section** - Add new section to results card, style it appropriately, handle cases where comments aren't available
- **Block 5: Error handling and edge cases** - Handle videos with comments disabled, API failures, rate limits, empty comment sections

## Backlog

- Implement Google OAuth authentication for user accounts
- Re-enable reports page with per-user data isolation (users only see their own reports)
- Dynamic UI based on archetype (TLDR = compact card, KEY_POINTS = bullet list layout, DEEP_DIVE = article format)
- Handle videos without transcripts (error message/fallback)
- Break down performance by API (YouTube fetch vs GPT processing time)

---

## Session History

**Thursday Jan 16 (50 minutes):** Continued Block 2 implementation for YouTube comments fetching. Used Postman to inspect actual API response structure and discovered each CommentThread contains both a top-level comment (via snippet.topLevelComment) and optional replies. Redesigned YoutubeCommentsResponse DTO with separate ThreadSnippet and CommentSnippet classes to match nested structure. Wired getComments() into analyze endpoint and started implementing extraction logic but identified filter/map logic bug before completing - filtering for replies existence but extracting top-level comments, which excludes comments without replies. Next session: Fix stream logic to collect both top-level and reply comments correctly.

**Wednesday Jan 14 (60 minutes):** Debugged confusing "Cannot access" error in YoutubeService trying to call getReplies() on CommentThread objects. Maven compilation succeeded but IDE showed red squiggles. Root cause was nested classes lacking public visibility modifier - package-private nested classes in dto package weren't accessible from service package. Fixed by adding public keyword to all nested classes in YoutubeCommentsResponse.

**Monday Jan 12 (60 minutes):** Planned YouTube comments integration feature. Removed user feedback from backlog, discussed API key authentication vs OAuth for comments access, and created 5-block sprint plan (design, API integration, LLM integration, UI update, error handling). Started Block 1 design discussions around service architecture and data structures.

**Sunday Jan 11 (60 minutes):** Redesigned homepage UI with Medium/Substack-inspired clean aesthetic. Implemented new HTML structure with header navigation, hero section, input area, and floating results card. Replaced gray background with pure white throughout, using stronger shadows and borders for elevation instead of color contrast, and adjusted spacing to be more condensed.

**Friday Jan 9 (50 minutes):** Encountered YouTube bot detection blocking transcript fetching in Railway production (data center IPs flagged as bots). Researched solutions including official API (OAuth restrictions), proxies (cost), and client-side fetching (CORS blocked). Set up Cloudflare Tunnel to route public traffic through residential IP while maintaining accessibility.

**Thursday Jan 8 (75 minutes):** Fixed Railway deployment (missing database env vars), completed Blocks 1, 2, and 4 using parallel agents. Block 3 layout needs refinement.

**Wednesday Jan 7 (45 minutes):** Partially fixed URL parsing bug and debugged Hibernate PostgreSQL dialect issue. Identified Block 2's root cause (array-to-string coercion in JavaScript).

**Monday Jan 5 (45 minutes):** Reorganized TODO.md with correct session dates and consolidated multi-session days. Identified critical UX issues through actual app usage: URL parsing bug with double equals signs, bullet point formatting broken in frontend, narrow content width wasting screen space, and need for performance tracking. Replaced vague sprint blocks with 4 concrete, actionable fixes.

---

## Past Weeks

<details>
<summary><b>Week of Dec 29, 2025 - Jan 4, 2026: Web UI Sprint</b></summary>

**Sunday Jan 4 (60 minutes):** Redesigned analysis archetypes (TLDR, KEY_POINTS, DEEP_DIVE) after discovering old ones produced identical outputs. Created three scope-based agents (backend, frontend, infra) and delegated PostgreSQL setup, archetype filtering, and prompt refinement. Learned prompt engineering principles and debugged agent-introduced bugs.

**Friday Jan 2 (50 minutes):** Built reports history page with table displaying all analyzed reports. Implemented loading indicators and debugged browser caching issues using DevTools "Disable cache" checkbox.

**Thursday Jan 1 (75 minutes total):**
- Session 1 (45 min): Implemented archetype feature with AnalysisRequest DTO and radio buttons. Created buildPrompt() method with switch expression. Discovered caching bug where same video with different archetypes returns cached result.
- Session 2 (30 min): Fixed caching bug by adding archetype field to Report entity with @Enumerated(EnumType.STRING) and creating findByPostIdAndArchetype method. Same video with different archetypes now generates fresh summaries.

**Wednesday Dec 31 (45 minutes):** Debugged "duplicate video bug" (user error). Confirmed API works correctly and planned Web UI Sprint with 5 blocks.

**Tuesday Dec 30 (90 minutes total):**
- Session 1 (30 min): Created frontend scaffolding (index.html, style.css, app.js) and configured Spring Boot to serve static files with live reload.
- Session 2 (60 min): Wired Analyze button to /analyze endpoint with fetch(). Debugged JavaScript async issues and got basic analyze flow working with summary display.

**Monday Dec 29:** Fixed Lombok Maven issues, deployed to Railway, downgraded to Java 17 for compatibility.

</details>

<details>
<summary><b>Week of Dec 22-28, 2025</b></summary>

Designed Transcript entity, implemented getTranscript(), swapped to official OpenAI SDK, completed MVP with YouTube transcripts and GPT-4 summarization.

</details>
