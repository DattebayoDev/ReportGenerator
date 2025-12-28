# Learning Topics

Topics and concepts I struggled with during development. Use these to create flashcards.

---

## Monday Dec 22, 2025

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

---

## Tuesday Dec 23, 2025

**When designing an API client, why might you implement a method that lists available resources separately from a method that fetches a specific resource, rather than combining them into one call?**

**When consuming external data that exists in multiple versions with varying quality (e.g., manually curated vs auto-generated), what factors should influence your fallback logic design?**

**If an external resource is available in multiple languages or formats, what strategy should you use to select the most appropriate one for your application's needs?**

---

## Friday Dec 26, 2025

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

---

## Saturday Dec 27, 2025

**When a third-party library conflicts with your framework's core dependency (e.g., Jackson version mismatch), what are the trade-offs between downgrading the framework vs replacing the library?**

**What is "blast radius of change" in software architecture, and how does loose coupling minimize it when swapping dependencies?**

**When two versions of the same dependency exist in a Maven project (one from your framework, one from a third-party library), which version gets loaded at runtime and why?**

**What's the difference between OS environment variables and application-specific configuration files (like application.properties), and why can't third-party Java libraries read Spring Boot configuration?**

**Before adding a third-party library dependency, what indicators on GitHub suggest it might be unmaintained or incompatible with modern frameworks?**

**If you isolate third-party library usage to a single service class (e.g., LlmService), how many files need changes when swapping that library for a different one?**

---

## Sunday Dec 28, 2025

**When saving two entities with a one-to-one relationship where Entity A has a foreign key to Entity B, which entity must be saved first and why does the order matter?**

**If a database save operation for Entity A succeeds, but then an exception is thrown before Entity B is saved, what happens to the data? What is transaction rollback and when does it occur?**

**What is the difference between `@Value("${property.name}")` reading from application.properties vs `System.getenv("ENV_VAR")` reading from OS environment variables?**

**When should you use application.properties vs environment variables for configuration in Spring Boot?**

**In the OpenAI Java SDK, what is the difference between the Responses API (`ResponseCreateParams`) and the Chat Completions API (`ChatCompletionRequest`)?**

**When chaining method calls like `response.output().getFirst().message().get().content()`, what does the `.get()` method typically indicate, and what Java construct is it commonly associated with?**

**If you're reading third-party library documentation to implement an integration, what's more important: understanding why the API is designed that way, or just following the example code to make it work?**
