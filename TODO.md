# Report Generator - Progress Tracker

*Rolling 2-week window. Older weeks will be archived to HISTORY.md.*

---

## Week of Dec 30, 2025 - Jan 4, 2026: Web UI Sprint

**Status:** Web UI Sprint complete! Redesigned archetypes (TLDR, KEY_POINTS, DEEP_DIVE), added filtering to reports page, set up PostgreSQL for production persistence. Ready for final testing.

**Web UI Blocks:**
- **Block 1: HTML form with URL input and archetype selection** ✓
- **Block 2: Wire form to /analyze endpoint and display summary** ✓
- **Block 3: Fix View Reports button and build reports history page** ✓
- **Block 4: Deploy updated UI to Railway and test in production** ✓
- **Block 5: Redesign archetypes and fix prompt differentiation** ✓
- **Block 6: Add archetype filtering to reports page** ✓
- **Block 7: Set up PostgreSQL for Railway persistence** ✓

**Future Work:**
- Handle videos without transcripts (error message/fallback)
- Source and analyze YouTube/Reddit comments
- User feedback feature for friends using the app

---

## Session History

<details>
<summary><b>Saturday Jan 4 (60 minutes)</b></summary>

Redesigned analysis archetypes (TLDR, KEY_POINTS, DEEP_DIVE) after discovering old ones produced identical outputs. Created three scope-based agents (backend, frontend, infra) and delegated PostgreSQL setup, archetype filtering, and prompt refinement. Learned prompt engineering principles and debugged agent-introduced bugs.

</details>

<details>
<summary><b>Friday Jan 3 (50 minutes)</b></summary>

Built reports history page with table displaying all analyzed reports. Implemented loading indicators and debugged browser caching issues using DevTools "Disable cache" checkbox.

</details>

<details>
nFixed caching bug by adding archetype field to Report entity with @Enumerated(EnumType.STRING) and creating findByPostIdAndArchetype method. Same video with different archetypes now generates fresh summaries.


</details>

<details>
<summary><b>Thursday Jan 2 - Session 1 (45 minutes)</b></summary>

Implemented archetype feature with AnalysisRequest DTO and radio buttons. Created buildPrompt() method with switch expression. Discovered caching bug where same video with different archetypes returns cached result.

</details>

<details>
<summary><b>Wednesday Jan 1 - Session 2 (60 minutes)</b></summary>

Wired Analyze button to /analyze endpoint with fetch(). Debugged JavaScript async issues and got basic analyze flow working with summary display.

</details>

<details>
<summary><b>Wednesday Jan 1 - Session 1 (30 minutes)</b></summary>

Created frontend scaffolding (index.html, style.css, app.js) and configured Spring Boot to serve static files with live reload.

</details>

<details>
<summary><b>Tuesday Dec 31 (45 minutes)</b></summary>

Debugged "duplicate video bug" (user error). Confirmed API works correctly and planned Web UI Sprint with 5 blocks.

</details>

---

## Past Weeks

<details>
<summary><b>Week of Dec 29, 2025 - Jan 4, 2026: Railway Deployment Sprint</b></summary>

Mon Dec 29: Fixed Lombok Maven issues, deployed to Railway, downgraded to Java 17 for compatibility.

</details>

<details>
<summary><b>Week of Dec 22-28, 2025</b></summary>

Designed Transcript entity, implemented getTranscript(), swapped to official OpenAI SDK, completed MVP with YouTube transcripts and GPT-4 summarization.

</details>
