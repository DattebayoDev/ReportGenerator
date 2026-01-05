# Report Generator - Progress Tracker

*Rolling 2-week window. Older weeks will be archived to HISTORY.md.*

---

## Week of Jan 5-11, 2026: Error Handling & Comments Integration Sprint

**Status:** Starting new sprint focused on robustness and richer analysis sources.

**Blocks:**
- **Block 1: Handle videos without transcripts - backend error handling**
- **Block 2: Handle videos without transcripts - frontend error display**
- **Block 3: Integrate YouTube comments API**
- **Block 4: Add YouTube comments to analysis prompts**
- **Block 5: Integrate Reddit comments API**
- **Block 6: Add Reddit comments to analysis prompts**
- **Block 7: Design user feedback feature (schema + API)**

**Future Work:**
- Build user feedback frontend
- Deploy comments integration to production
- Performance testing with longer videos

---

## Session History

*Sessions for Week of Jan 5-11, 2026 will appear here.*

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
