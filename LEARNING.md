# Learning Topics

Topics and concepts I struggled with during development. Use these to create flashcards.

---

## Monday Dec 22, 2025

**What does `@OneToOne` annotation do in JPA?**

**What is the purpose of `@JoinColumn(name = "report_id")` in a Transcript entity?**

**Why do you need a setter method for a relationship field (e.g., `setReport(Report report)`)?**

**What does `cascade = CascadeType.ALL` do in JPA relationships?**

**When should you NOT use cascade operations?**

**Why separate Transcript into its own entity instead of adding it as a field in Report?**

**Should metadata and transcript be fetched together or separately, and why?**

**What's the benefit of splitting `YoutubeService.getData()` into `getMetadata()` and `getTranscript()`?**

**Who should decide which service methods to call - the controller or the service layer?**

**What is a foreign key in database terms?**

**In a one-to-one relationship between Report and Transcript, which table should hold the foreign key?**

**What's the performance impact of always fetching both metadata and transcript when only one is needed?**

**What does `@Column(columnDefinition = "TEXT")` do in JPA?**

**How does Spring Data JPA implement custom query methods like `findByVideoId(String videoId)`?**
