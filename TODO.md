# Report Generator - Progress Tracker

*Rolling 2-week window. Older weeks will be archived to HISTORY.md.*

---

## Week of Jan 5-11, 2026: UX Polish & Bug Fixes Sprint

**Status:** Fixing critical bugs and improving content readability.

**Blocks:**
- **Block 1: Fix URL parsing bug (double equals signs in URLs like &pp= causing failures)**
- **Block 2: Fix frontend rendering (bullet points showing as "0.1\n0.2" instead of formatted list)**
- **Block 3: Update layout width (change to 80% screen width, reduce white space)**
- **Block 4: Add performance tracking (track total time from analyze click to response received)**

**Future Work:**
- Handle videos without transcripts (error message/fallback)
- Break down performance by API (YouTube fetch vs GPT processing time)
- Integrate YouTube/Reddit comments for richer analysis
- User feedback feature for friends using the app

---

## Session History

**Tuesday Jan 7 (45 minutes):** Started Block 1 by investigating URL parsing bug with double equals signs in query parameters. Partially fixed the parsing logic but didn't cover all edge cases yet. Also debugged production deployment error where Hibernate couldn't find PostgreSQL10Dialect class. Learned about Hibernate 6's move from version-specific dialects to feature detection pattern. Identified Block 2's root cause: frontend JavaScript splits summary by newlines into an array, then assigns array to textContent which triggers implicit array-to-string coercion showing comma-separated values instead of formatted list. Next session should either complete Block 1 edge cases or move to fixing Block 2's rendering issue.

**Sunday Jan 5 (45 minutes):** Reorganized TODO.md with correct session dates and consolidated multi-session days. Identified critical UX issues through actual app usage: URL parsing bug with double equals signs, bullet point formatting broken in frontend, narrow content width wasting screen space, and need for performance tracking. Replaced vague sprint blocks with 4 concrete, actionable fixes.

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
