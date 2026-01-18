# Learning - Flashcard Questions

*Rolling 2-week window. Older questions will be archived to HISTORY.md.*

Transferable programming concepts that came up during development. Questions are designed for spaced repetition and flashcard review.

---

## Week of Dec 22-28, 2025

### Monday Dec 22
Worked on designing the Transcript entity and understanding JPA relationships. Spent a lot of time wrapping my head around foreign keys and when to decouple service methods.

**What does `@OneToOne` annotation do in JPA?**

**What is the purpose of `@JoinColumn(name = "report_id")` in a Transcript entity?**

**Why do you need a setter method for a relationship field like `setReport(Report report)`?**

**What is a foreign key in database terms?**

**In a one-to-one relationship between Report and Transcript, which table should hold the foreign key?**

**Why separate Transcript into its own entity instead of adding it as a field in Report?**

**Should metadata and transcript be fetched together or separately, and why?**

**What's the benefit of decoupling YoutubeService into `getMetadata()` and `getTranscript()` methods?**

**Who should decide which service methods to call - the controller or the service layer?**

**What does `@Column(columnDefinition = "TEXT")` do in JPA?**

### Tuesday Dec 23
Started implementing getTranscript() and discovered that videos can have multiple transcripts with varying quality. Had to think through API client design patterns and fallback logic.

**When designing an API client, why might you implement a method that lists available resources separately from a method that fetches a specific resource, rather than combining them into one call?**

**When consuming external data that exists in multiple versions with varying quality (e.g., manually curated vs auto-generated), what factors should influence your fallback logic design?**

**If an external resource is available in multiple languages or formats, what strategy should you use to select the most appropriate one for your application's needs?**

### Friday Dec 26
Completed the transcript fetching implementation using streams. Learned about StringBuilder, immutability, factory patterns, and the importance of understanding when to stop exploring third-party code.

**What is StringBuilder and why use it instead of string concatenation in a loop?**

**Why are Strings immutable in Java, and what problem does this create when concatenating in a loop?**

**When processing a list of items to combine into a single result, what are the two main approaches in Java?**

**When reading unfamiliar third-party library code, what should guide how deeply you explore?**

**What's the difference between "not knowing Java vocabulary" vs "not being able to think analytically"?**

**Why can't you instantiate an interface directly, and what do you need instead?**

**What does it mean when a class constructor has no access modifier (package-private)?**

**What is a factory method pattern and why do libraries use it instead of exposing constructors directly?**

**When saving entities with a one-to-one relationship, why does the order of saves matter?**

**If Entity A has a foreign key to Entity B, which entity must be saved first and why?**

### Saturday Dec 27
Ran into a Jackson version conflict between Spring Boot and an archived library. Had to research dependency management, learned about blast radius of change, and swapped to a maintained library. Also discovered the difference between environment variables and application.properties.

**When a third-party library conflicts with your framework's core dependency (e.g., Jackson version mismatch), what are the trade-offs between downgrading the framework vs replacing the library?**

**What is "blast radius of change" in software architecture, and how does loose coupling minimize it when swapping dependencies?**

**When two versions of the same dependency exist in a Maven project (one from your framework, one from a third-party library), which version gets loaded at runtime and why?**

**What's the difference between OS environment variables and application-specific configuration files (like application.properties), and why can't third-party Java libraries read Spring Boot configuration?**

**Before adding a third-party library dependency, what indicators on GitHub suggest it might be unmaintained or incompatible with modern frameworks?**

**If you isolate third-party library usage to a single service class (e.g., LlmService), how many files need changes when swapping that library for a different one?**

### Sunday Dec 28
Session crashed but recovered context. During code review, identified several knowledge gaps around JPA save ordering, transaction rollback, configuration patterns, and the OpenAI SDK. Understanding why code works is different from making it work.

**When saving two entities with a one-to-one relationship where Entity A has a foreign key to Entity B, which entity must be saved first and why does the order matter?**

**If a database save operation for Entity A succeeds, but then an exception is thrown before Entity B is saved, what happens to the data? What is transaction rollback and when does it occur?**

**What is the difference between `@Value("${property.name}")` reading from application.properties vs `System.getenv("ENV_VAR")` reading from OS environment variables?**

**When should you use application.properties vs environment variables for configuration in Spring Boot?**

**In the OpenAI Java SDK, what is the difference between the Responses API (`ResponseCreateParams`) and the Chat Completions API (`ChatCompletionRequest`)?**

**When chaining method calls like `response.output().getFirst().message().get().content()`, what does the `.get()` method typically indicate, and what Java construct is it commonly associated with?**

**If you're reading third-party library documentation to implement an integration, what's more important: understanding why the API is designed that way, or just following the example code to make it work?**

---

## Week of Dec 29, 2025 - Jan 4, 2026

### Monday Dec 30
Ran into Lombok annotation processing issues with Maven CLI. The app compiled fine in IntelliJ but failed during mvn clean install. Had to configure Maven's compiler plugin to enable annotation processing and learned about the difference between IDE compilation and Maven CLI compilation.

**Why might Lombok annotations work in IntelliJ but fail when running mvn clean install from the command line?**

**What is annotation processing in Java compilation, and why does it need to be explicitly enabled in Maven's compiler plugin?**

**In Maven's compiler plugin configuration, what does the `<annotationProcessorPaths>` element do, and why is it needed for Lombok?**

**What are the different modes of `spring.jpa.hibernate.ddl-auto` and what does each one do?**

**What's the difference between `ddl-auto=update` and `ddl-auto=create` in terms of data persistence?**

**If you see a "table not found" error in a Spring Boot JPA application, but your @Entity classes are correctly annotated, what configuration property is likely missing?**

**What does Hibernate automatically generate from your @Entity classes when spring.jpa.hibernate.ddl-auto is set to update or create?**

**When using H2 file-based persistence with jdbc:h2:file:./data/reportdb, what physical files are created on disk and what do they contain?**

### Tuesday Dec 31
Encountered Java version compatibility issues when downgrading from Java 24 to Java 17 for Railway deployment. Ran into a compilation error with List.getFirst() because that method didn't exist in Java 17.

**When you see a "cannot find symbol" error for a method on a standard Java class like List, what should you suspect about the Java version you're using?**

**How do you determine which Java version introduced a specific method like List.getFirst()?**

**If you downgrade from Java 21+ to Java 17, what happens to code using List.getFirst() or List.getLast(), and how do you fix it?**

**What's the difference between a compilation error saying "cannot find symbol" for a method vs other types of compilation errors?**

**When targeting a specific Java version (e.g., Java 17), why isn't it enough to just set the version in pom.xml - what else could break?**

**If a method exists in newer Java versions but not in your target version, what are your options for achieving the same functionality?**

### Tuesday Dec 30
Debugged what I thought was a duplicate video bug but turned out to be user error - I was sending the same request body repeatedly. This led to discussing request body vs query parameters, URL encoding, JSON serialization, and API design patterns. Realized the real blocker to using the product was lack of UI, not bugs.

**When designing a REST API endpoint that accepts user input, what are the main factors that determine whether to use query parameters vs request body?**

**Why do URLs containing special characters like `?`, `&`, `#`, and `/` require URL encoding when sent as query parameters, and why is this error-prone?**

**If you're building an API endpoint that accepts a URL as input, why is request body preferred over query parameters?**

**What does it mean for an HTTP request to be "idempotent", and how does this concept influence whether an endpoint should use GET vs POST?**

**If an endpoint sometimes creates new data (on first call) but sometimes just reads existing data (on subsequent calls with same input), should it use GET or POST, and why?**

**When you see an SDK using a builder pattern like `Params.builder().field1(value).field2(value).build()` followed by `client.create(params)`, what HTTP method and request format is it most likely using under the hood?**

**What is the difference between a server's responsibility (serializing data to JSON) and a client's responsibility (rendering/displaying that JSON) in web applications?**

**If your API correctly returns JSON with Content-Type: application/json but it appears as one long line in the browser, is this a server bug or expected behavior?**

### Wednesday Dec 31
Started the Web UI sprint by creating the frontend scaffolding. Had to figure out where static files live in Spring Boot and how to enable live reload during development. Debugged JavaScript async issues and learned about the difference between properties and methods in the DOM API.

**In Spring Boot, what directory should static files (HTML, CSS, JS) be placed in for automatic serving without a controller?**

**Spring Boot auto-configures a resource handler that maps `/**` to which classpath locations for static files?**

**When deciding whether to keep frontend and backend in the same project vs separate repositories, what are the key trade-offs to consider?**

**For a simple HTML/CSS/JS frontend that calls your API, why is same-project simpler than separate projects?**

**What Spring Boot property allows static files to reload on browser refresh without restarting the application, and how does it work?**

**When using `spring.web.resources.static-locations=file:path`, what determines whether the path resolves correctly?**

**In HTML, what happens if you place a button with `type="submit"` inside a form element, and how does this affect button behavior?**

**Why does `const` in JavaScript require an initializer (e.g., `const x;` is invalid) while `let` does not?**

**What is the fundamental difference between `const`, `let`, and `var` in terms of reassignment and scope?**

**When using `fetch()` in JavaScript, why can't you access the response data outside the `.then()` callback, even if you store it in a variable declared outside?**

**What does "asynchronous execution" mean in JavaScript, and how does it affect the order in which code runs?**

**In the JavaScript DOM API, what's the difference between `element.textContent` and a method like `element.appendChild()`?**

**If you see a JavaScript error "X is not a function", what does that tell you about how you're trying to use X?**

**In JavaScript strings, how do you represent a literal backslash character, and why does `'\n\'` cause a syntax error?**

**When `JSON.stringify()` is applied to a string that contains newline characters (`\n`), what happens to those newlines?**

### Thursday Jan 1
Implemented analysis archetype feature allowing users to select different summarization styles (12-year-old, entry-level, high-level, custom). Designed the data contract between frontend and backend, created an enum for type safety, and wired the archetype through the service layer to customize LLM prompts.

**In Spring MVC, how many `@RequestBody` parameters can a single controller method accept, and why does this constraint exist?**

**When designing a DTO that combines multiple pieces of related input (like URL and analysis mode), what principle guides whether to create one combined DTO vs multiple separate parameters?**

**What is the difference between a DTO (Data Transfer Object) and an Entity in Spring Boot/JPA applications, specifically regarding database persistence?**

**When should you use Java enums for frontend-to-backend communication, and how does Jackson handle deserializing string values from JSON into Java enum constants?**

**If you have logic that maps enum values to specific behavior (like TWELVE_YEAR_OLD → specific prompt text), where should that mapping logic live: controller, service, or utility class? What principle guides this decision?**

**What is a switch expression in Java (introduced in Java 14), and how does it differ from a traditional switch statement in terms of returning values?**

**When building a caching or lookup mechanism that checks if data already exists before processing, what happens when you add a new dimension to your data (e.g., same video with different analysis modes)? How does this affect your lookup key?**

**If your database lookup uses only `postId` to check if a video was already analyzed, but now the same video can have multiple summaries (one per archetype), what architectural change is needed to the lookup logic?**

**What is the difference between `file:` and `classpath:` resource protocols in Spring Boot, and when should you use each?**

**When deploying a Spring Boot app to a cloud platform like Railway or Heroku, why does `spring.web.resources.static-locations=file:...` fail to serve static files?**

**How does Spring Boot package applications for production deployment, and why does this affect how static resources are accessed?**

**What is Spring Boot's default location for serving static files (HTML, CSS, JS) without any configuration overrides?**

**If static files work locally but return 404 in production, and you have a `spring.web.resources.static-locations` override using `file:`, what's likely the problem?**

### Thursday Jan 2
Fixed the caching bug where analyzing the same video with different archetypes returned cached results instead of generating new summaries. Added archetype field to Report entity and updated lookup logic to check both postId and archetype. Discussed database migration strategies and when to use explicit migrations versus ddl-auto in production.

**When JPA encounters a custom Java enum type as an entity field, what annotation is required to specify how it should be stored in the database?**

**What does `@Enumerated(EnumType.STRING)` tell JPA to do when storing an enum value in the database?**

**What does `@Enumerated(EnumType.ORDINAL)` store in the database, and why is this dangerous for long-term maintenance?**

**If you have an enum with values [A, B, C, D] stored using EnumType.ORDINAL, and you later reorder it to [A, X, B, C, D], what happens to existing database records that stored "B" as 1?**

**Why do experienced developers always explicitly use `@Enumerated(EnumType.STRING)` instead of relying on JPA's default behavior?**

**When adding a new column to an existing database table in production with real user data, why can't you just rely on `spring.jpa.hibernate.ddl-auto=update`?**

**What are Flyway and Liquibase, and what problem do they solve that ddl-auto doesn't?**

**When adding a nullable column to a table with existing rows, what value will existing rows contain for that new column after the migration runs?**

**If you add a new field to an entity and need to support old records that don't have that field populated, what are your options for handling null values in application code?**

**In a small side project with test data versus a production system with real users, how does this context change your approach to database schema changes?**

### Thursday Jan 2
Built the reports history page from scratch. Created a table that fetches and displays all analyzed reports on page load. Ran into browser caching issues where JavaScript changes weren't appearing despite restarting Spring Boot and running mvn clean package. Learned about the multiple caching layers between code and browser.

**When developing a web application, what are the three main caching layers that can prevent your code changes from appearing in the browser?**

**If you modify a JavaScript file and restart your Spring Boot application, but the browser still shows old code in DevTools Sources tab, which caching layer is the culprit?**

**What is the difference between a normal browser refresh (F5) and a hard refresh (Ctrl+F5 or Ctrl+Shift+R) in terms of cache behavior?**

**In Chrome DevTools Network tab, what does the "Disable cache" checkbox do, and when is it most useful?**

**During local development, does Spring Boot serve static files from the compiled JAR or directly from src/main/resources/static? How does this differ from production deployment?**

**If you're making frontend changes (HTML/CSS/JS) during development, do you need to run mvn clean package for those changes to appear, or is restarting the Spring Boot application sufficient?**

**What is the window.onload event in JavaScript, and when does it fire during the page lifecycle?**

**What is the difference between window.onload and DOMContentLoaded events in terms of what they wait for before firing?**

**When implementing a loading indicator for data that loads asynchronously, what is the typical state management pattern (what gets shown/hidden and when)?**

**In CSS, what does border-collapse: collapse do for HTML tables, and how does it differ from the default border-collapse: separate?**

**When styling specific columns in an HTML table, what CSS selector allows you to target the 3rd column without adding classes to every cell?**

**If you want to prevent long URLs from breaking your table layout, what CSS property forces text to wrap even in the middle of words?**

**When a browser caches static assets like JavaScript files, what HTTP headers from the server control how long the browser keeps that cached version?**

**Why might browser caching be more aggressive for .js and .css files compared to .html files?**

### Saturday Jan 4
Redesigned the analysis archetypes after discovering all three produced nearly identical outputs. Learned prompt engineering principles, created custom Claude Code agents for delegating work, and set up PostgreSQL for production persistence.

**When writing LLM prompts, why should format instructions come before the actual question or task?**

**If an LLM prompt leads with a question like "Is this worth watching?", what type of response does it invite, and why might this conflict with strict format requirements?**

**When multiple instructions in an LLM prompt contradict each other (e.g., "this is analysis, not summary" followed by "only give me the summary"), which instruction typically wins, and why?**

**What's the difference between scope-based agents (frontend, backend, infra) vs task-specific agents (db-migration, prompt-refiner) when designing reusable automation?**

**What's the difference between surface-level bugs (typos, wrong variable names) and deep bugs (architectural issues, framework misunderstandings, edge cases) in terms of learning value?**

**Why do file-based databases like H2 not persist data across deployments on cloud platforms like Railway or Heroku?**

**What is ephemeral storage in containerized deployments, and how does it affect application state?**

**In Spring Boot, what is the naming convention for profile-specific configuration files, and how does Spring know which one to load?**

**If you have application-prod.properties and application-local.properties but no base application.properties, what must be true for either config to load?**

**When Railway provisions a PostgreSQL database, what environment variables does it automatically create, and why don't you need to manually set credentials?**

### Sunday Jan 5
Cleaned up TODO.md session history and planned the new sprint. Identified critical UX issues in the deployed app (formatting, layout, performance tracking) through actual usage. Discussed sprint planning principles and the importance of concrete, actionable task breakdown.

**When planning sprint tasks or "blocks" of work, what characteristics make a task concrete and actionable vs vague and hard to start?**

**What is decision fatigue in software development, and how does poor task breakdown at the sprint level contribute to it?**

**When is investing time in process optimization (like improving sprint planning or git workflows) worth the upfront cost vs being a form of analysis paralysis?**

**In a git workflow, what's the conceptual difference between staging changes, committing them, and pushing them? Why are these three separate operations instead of one?**

**When identifying bugs or UX issues in a deployed application through actual usage, why should you investigate and understand the root cause before jumping to implementation?**

**What makes a good "demo moment" or success criteria for a sprint, and why is defining this upfront important for task selection?**

**If you finish a project and deploy it to production, but then identify UX issues through usage, does this mean the project "failed" or is this normal for iterative development?**

### Tuesday Jan 7
Worked on URL parsing bug and encountered production deployment issue with Hibernate dialect. Learned about regex quantifiers, library evolution patterns, and JavaScript's array-to-string coercion behavior.

**In regex patterns, what does the quantifier `{n}` mean, and how is it different from `*` or `+`?**

**If a regex pattern is `[a-zA-Z0-9_-]{11}`, what does this match in plain English?**

**When configuring production environments, what are the risks of using `spring.jpa.hibernate.ddl-auto=update` with real user data?**

**What is the difference between explicit configuration (specifying values in properties files) vs implicit/auto-detection (letting the framework figure it out), and when should you prefer each approach?**

**How do mature libraries evolve to avoid creating version-specific classes forever (e.g., PostgreSQL10Dialect, PostgreSQL11Dialect, PostgreSQL12Dialect)? What design pattern replaces version detection?**

**What is feature detection in library design, and how does it differ from version detection?**

**In JavaScript, what happens when you assign an array to an element's `textContent` property?**

**If you have an array `["Line 1", "Line 2", "Line 3"]` and assign it to `div.textContent`, what string appears in the div?**

**What is JavaScript's implicit array-to-string coercion behavior, and what method gets called automatically during this conversion?**

**What's the difference between `textContent` and `innerHTML` in the DOM API, specifically regarding how they handle HTML tags and special characters?**

### Thursday Jan 8
Debugged Railway production deployment where the database connection failed despite successful deployment. Learned about service isolation in cloud platforms and the difference between deployment success and application runtime success. Set up Spring Boot Actuator for performance monitoring and debugged endpoint exposure configuration.

**In cloud platforms that separate infrastructure into independent services (database, application, cache), do environment variables from one service automatically propagate to other services? How are credentials shared between services?**

**What's the difference between "deployment successful" and "application running successfully" in containerized cloud deployments? Why can a deployment succeed but the application still fail to respond to requests?**

**If your Spring Boot application startup logs show "Failed to initialize JPA EntityManagerFactory: Unable to open JDBC Connection", but your datasource configuration uses environment variables like ${PGHOST}, what should you verify first?**

**When debugging cloud deployment issues where local development works but production fails, what's the systematic approach to verifying environment-specific configuration (database URLs, API keys, service endpoints)?**

**In Spring Boot, if you have multiple profile-specific properties files (application-local.properties, application-prod.properties) but don't set SPRING_PROFILES_ACTIVE, which configuration gets loaded and what happens to your app?**

**What is Spring Boot Actuator, and what's the difference between developer-facing metrics (/actuator/metrics) vs user-facing performance data returned in API responses?**

**If Spring Boot Actuator's /actuator/health endpoint works but /actuator/metrics returns 404, despite both being listed in management.endpoints.web.exposure.include, what are the likely causes?**

**When displaying formatted text in a webpage, what are the three approaches (textContent, innerHTML, DOM manipulation), and what are the security and formatting trade-offs of each?**

### Thursday Jan 9
Encountered YouTube bot detection blocking transcript fetching in Railway production (worked locally). Researched YouTube's official API and discovered OAuth limitations prevent downloading transcripts from videos you don't own. Explored scraping approaches, proxy solutions, and cloud deployment architectures. Decided on local deployment with Cloudflare Tunnel to use residential IP while maintaining public accessibility.

**When YouTube (or any website) detects bot traffic, what are the three main characteristics they analyze to distinguish automated requests from real browser traffic?**

**What's the difference between residential IP addresses (home ISPs) and data center IP addresses (cloud providers like AWS, Railway, Heroku) from a bot detection perspective?**

**If your Spring Boot application works locally but gets blocked by YouTube's bot detection when deployed to Railway/Heroku, which of these factors is most likely the root cause: (1) HTTP headers, (2) IP address reputation, or (3) request frequency?**

**What is OAuth 2.0 authorization in the context of YouTube's Data API, and why does it prevent you from downloading captions/transcripts for videos you don't own?**

**Why can't the official YouTube Data API v3 be used to build a service that summarizes arbitrary public YouTube videos? What business incentives drive this API design decision?**

**What is CORS (Cross-Origin Resource Sharing), and why does the browser block your frontend JavaScript (running on yourapp.com) from making requests directly to youtube.com?**

**When a browser enforces CORS, at what point in the request lifecycle does the block occur: (1) before the request is sent, (2) after the request is sent but before the response arrives, or (3) after the response arrives but before JavaScript can access it?**

**What's the difference between server-side HTTP requests (from your Spring Boot backend) and client-side HTTP requests (from JavaScript in the browser) in terms of CORS enforcement?**

**What is a residential proxy service, and how does it help bypass bot detection compared to free proxy lists or data center proxies?**

**When comparing deployment architectures for a web scraper that gets blocked in the cloud, what are the trade-offs between: (1) local deployment + tunneling service, (2) cloud deployment + residential proxy service, and (3) browser extension?**

**What is ngrok, and how does it allow a locally-running application to be accessible via a public HTTPS URL?**

**What is Cloudflare Tunnel (formerly Argo Tunnel), and how does its free quick tunnel feature differ from ngrok's free tier in terms of warning pages and user experience?**

**When you run `cloudflared tunnel --url http://localhost:8080`, what happens to requests from the internet? Trace the path from external user → your local application.**

**If you add custom HTTP headers (like 'ngrok-skip-browser-warning') to JavaScript fetch() calls, when do these headers get sent: (1) when loading the initial HTML page, (2) only on subsequent API calls from JavaScript, or (3) on every request including static assets?**

### Sunday Jan 11
Redesigned the entire frontend UI to look more professional with a Medium/Substack-inspired aesthetic. Implemented template structure while preserving all functional elements, adjusted spacing throughout, and used shadow/border elevation instead of background color contrast for visual hierarchy.

**When using CSS flexbox with `flex-direction: column` and `min-height: 100vh` on a container, how does `margin-top: auto` on the last child element work to push it to the bottom of the viewport?**

**In UI design, what are the tradeoffs between using subtle background color differences (like #fafafa vs #ffffff) versus relying entirely on shadows and borders for visual hierarchy and section separation?**

**When implementing a design template into an existing functional codebase, what's the systematic approach to preserve interactive elements (IDs, event handlers, form logic) while completely restructuring the visual HTML/CSS?**

**What's the CSS technique for creating a "floating card" effect where a white card appears elevated above a white background without any color contrast?**

**In flexbox layout, why does `margin-top: auto` absorb available space differently than explicitly setting a large margin value or using spacer divs?**

### Monday Jan 12
Started planning YouTube comments integration feature. Discussed API architecture decisions, clarified why YouTube Data API v3 treats comments differently from transcripts (OAuth not required for public comments), and broke down the feature into 5 implementation blocks.

**Why does YouTube Data API v3 require OAuth for accessing transcripts/captions but allow public comments to be fetched with just an API key?**

**When designing a new feature that requires calling an external API, what factors determine whether you need a separate controller endpoint versus integrating into an existing endpoint?**

**If you already have all required parameters to make an API call (videoId, API key) from an existing user request, why would you NOT need to expose a separate endpoint to the user?**

**When adding a new API integration to an existing service class (like YoutubeService), what are the tradeoffs between adding methods to the existing service versus creating a separate service class?**

**In Spring Boot with RestTemplate, when should you return raw API response data versus creating DTOs to wrap the response?**

**What's the difference between how YouTube's API treats "channel owner content" (transcripts) versus "public content" (comments) in terms of authentication requirements?**

### Wednesday Jan 14
Ran into a confusing IDE error where nested class methods appeared inaccessible even though Maven compilation succeeded. Debugged Java nested class visibility rules and learned about the difference between IDE analysis and actual compilation.

**What does the error message "Cannot access [method]" mean in Java, and how is it different from "Cannot find symbol" or "Method does not exist"?**

**In Java, what's the difference between a top-level class visibility rule (one public class per file) and nested class visibility rules?**

**Can nested classes (classes defined inside another class) be declared public, or are they restricted by the "one public class per file" rule?**

**If a nested class doesn't have the `public` keyword before `class`, what is its default visibility and which packages can access it?**

**When you encounter an IDE error showing red squiggles but `mvn clean compile` succeeds, what does this tell you about the validity of your code?**

**What are the two separate compilation/analysis systems at play when developing Java in an IDE, and which one determines if your code is actually valid?**

**What is Lombok's role in code generation, and why might an IDE show errors for Lombok-generated methods even when Maven compilation succeeds?**

**If nested classes in package `com.example.demo.dto` are package-private (no public keyword), can code in package `com.example.demo.service` call methods on objects of those nested class types? Why or why not?**

### Thursday Jan 16
Continued implementing YouTube comments integration. Discovered the actual API structure through Postman testing - each CommentThread has both a top-level comment (via snippet.topLevelComment) and optional replies. Updated the DTO to correctly model this nested structure by separating ThreadSnippet from CommentSnippet. Wired getComments() into the analyze flow and started implementing extraction logic, but identified a logical bug where filter condition doesn't match what's being extracted.

**When integrating with a third-party REST API, what are the tradeoffs between using the official SDK library versus making direct HTTP calls with RestTemplate and custom DTOs?**

**If you need to call only one or two endpoints from a large API (like YouTube Data API v3), what factors determine whether the official SDK dependency is worth adding to your project?**

**When mapping a JSON API response to Java DTOs, what's the systematic approach to ensure your class structure matches the actual response?**

**Why is testing with actual API responses (via curl or Postman) more reliable than trusting API documentation alone when building DTOs?**

**In Java streams, what's the difference between map() and flatMap() in terms of input-to-output cardinality?**

**If you have a stream where each input element can produce multiple output elements (e.g., 1 CommentThread → 1 top-level comment + N reply comments), which stream operation should you use?**

**When using stream operations like filter() followed by map(), what logical issue can arise if the filter checks one field but the map extracts a completely different field?**

**If you filter a stream to keep only items where field X exists (.filter(item -> item.getX() != null)), but then extract field Y (.map(item -> item.getY())), what data might you accidentally exclude?**

**When debugging complex stream chains, what's the systematic approach to verify what each operation (filter, map, flatMap) is doing to the data at each step?**

**In nested object structures like YouTube's comment threads, why might you need to create multiple DTO classes even though they have similar field names (e.g., ThreadSnippet vs CommentSnippet)?**

### Saturday Jan 17
Completed YouTube comments integration sprint (Blocks 1-4). Removed Transcript entity entirely (YAGNI - not using it). Restructured Report to store contentSummary and communityReaction separately. Created AnalysisResult DTO for structured LLM responses. Refined prompts to capture sentiment/tone instead of dry summaries. Updated frontend with grid layout (main content + community sidebar). **VERY IMPORTANT DEEP DIVE NEEDED**: Successfully configured H2 console to work with Spring Boot 4.0 by using JakartaWebServlet instead of WebServlet due to Jakarta EE vs javax servlet API mismatch.

**What is the YAGNI principle in software development, and how does it guide decisions about whether to keep or remove code/features?**

**When you have a feature that "might be useful later" (like storing raw transcripts for potential re-summarization), what are the costs of keeping it vs removing it?**

**If you're caching results by postId+archetype in the Report table, why might storing the original transcript in a separate Transcript table be redundant?**

**When designing database schemas, what's the difference between "this data is needed for current functionality" vs "this data might be useful someday"? Which should drive schema decisions?**

**When splitting a single field into multiple fields (e.g., summary → contentSummary + communityReaction), what database migration concerns exist for production systems with existing data?**

**What is a Java record, and why is it useful for DTOs that simply hold data without behavior?**

**When an LLM returns a single string response that contains multiple logical sections, what are the two main approaches to extract those sections programmatically?**

**If you use a delimiter like "---COMMUNITY REACTION---" to split LLM responses, why must you handle the case where the LLM doesn't include the delimiter? What's a good fallback strategy?**

**In prompt engineering, what's the difference between asking an LLM to "summarize the comments" vs "capture the TONE and SENTIMENT of the comments"? How does this affect output quality?**

**When prompting an LLM for personality and emotion, what specific techniques make responses feel more human? (e.g., asking "Are people excited? Skeptical? What are they debating?")**

**In CSS Grid, what does `grid-template-columns: 1fr 340px` mean, and how does the layout behave when the viewport is too narrow?**

**When creating a two-column layout where one column is fixed-width (sidebar) and one is flexible (main content), why use CSS Grid instead of flexbox?**

**In responsive design, when should a sidebar move from beside the main content to below it? What CSS property/media query pattern handles this?**

**⭐ VERY IMPORTANT - DEEP DIVE NEEDED: What is the difference between javax.servlet and jakarta.servlet packages, and why did this change happen in modern Java EE?**

**⭐ VERY IMPORTANT: Spring Boot 4.0 uses Jakarta EE. If an H2 dependency provides both WebServlet (javax.servlet) and JakartaWebServlet (jakarta.servlet), which one must you use and why?**

**⭐ VERY IMPORTANT: What is ServletContextInitializer in Spring Boot, and how does it allow manual servlet registration when auto-configuration doesn't work?**

**⭐ VERY IMPORTANT: When using ServletContextInitializer.onStartup(), what's the pattern for registering a servlet? (servletContext.addServlet, setLoadOnStartup, addMapping)**

**If Spring Boot's auto-configuration for a feature (like H2 console) doesn't work due to version incompatibilities, what are your debugging/fixing options?**

**When you see "Type parameter X is not within its bound; should implement Y", what does this error mean and how do you fix it?**

**What's the difference between `scope=runtime` and `scope=compile` for Maven dependencies, and why might changing scope affect which classes are available at compile time vs runtime?**
