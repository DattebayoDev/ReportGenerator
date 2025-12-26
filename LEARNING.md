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
