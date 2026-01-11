# Report Generator - Progress Tracker

*Rolling 2-week window. Older weeks will be archived to HISTORY.md.*

---

## Blocks

(No active blocks - ready for new sprint planning)

## Backlog

- Dynamic UI based on archetype (TLDR = compact card, KEY_POINTS = bullet list layout, DEEP_DIVE = article format)
- Handle videos without transcripts (error message/fallback)
- Break down performance by API (YouTube fetch vs GPT processing time)
- Integrate YouTube/Reddit comments for richer analysis
- User feedback feature for friends using the app

---

## Session History

**Saturday Jan 11 (60 minutes):** Redesigned homepage UI with Medium/Substack-inspired clean aesthetic. Implemented new HTML structure with header navigation, hero section, input area, and floating results card. Replaced gray background with pure white throughout, using stronger shadows and borders for elevation instead of color contrast, and adjusted spacing to be more condensed.

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
