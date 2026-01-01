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