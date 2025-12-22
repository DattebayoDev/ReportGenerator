# Learning Topics for Flashcards

This file tracks new concepts and topics discovered during development for flashcard creation and further study.

---

## Friday Dec 19, 2025 - JPA Relationships & Architecture

### JPA @OneToOne Relationship
**Question:** What does `@OneToOne` annotation do in JPA?
**Answer:** Defines a one-to-one relationship between two entities. Each instance of one entity corresponds to exactly one instance of another entity.

**Question:** What is the purpose of `@JoinColumn(name = "report_id")` in a Transcript entity?
**Answer:** Creates a foreign key column called `report_id` in the Transcript table that points to the primary key of the Report table. This links the two entities at the database level.

**Question:** Why do you need a setter method for a relationship field (e.g., `setReport(Report report)`)?
**Answer:** JPA needs to know which entity instance to link to when saving. Without the setter, you can't establish the relationship, and the foreign key column remains null or the save fails.

### Cascade Operations
**Question:** What does `cascade = CascadeType.ALL` do in JPA relationships?
**Answer:** Automatically propagates operations (save, delete, update, etc.) from parent entity to related entities. Without it, you must explicitly save each entity separately.

**Question:** When should you NOT use cascade operations?
**Answer:** When you want explicit control over when related entities are saved/deleted, or when the relationship lifecycle should be managed independently for clarity and safety.

### Database Design Decisions
**Question:** Why separate Transcript into its own entity instead of adding it as a field in Report?
**Answer:** Transcripts can be very large (thousands of words). Separating them improves performance (don't load transcript when only summary is needed), follows single responsibility principle, and prevents database row bloat.

**Question:** Should metadata and transcript be fetched together or separately?
**Answer:** Separately. They have different use cases, different performance characteristics, and decoupling prevents unnecessary API calls and improves flexibility.

### Architectural Patterns
**Question:** What's the benefit of splitting `YoutubeService.getData()` into `getMetadata()` and `getTranscript()`?
**Answer:** Decoupling allows calling only what's needed, reduces API quota usage, improves performance (transcripts are slower to fetch), and follows single responsibility principle.

**Question:** Who should decide which service methods to call - the controller or the service layer?
**Answer:** The controller. It knows the user's intent (what data is needed for the request) and can orchestrate service calls accordingly.

### Foreign Keys & Relationships
**Question:** What is a foreign key in database terms?
**Answer:** A column that contains the primary key of another table, creating a link between the two tables and enforcing referential integrity.

**Question:** In a one-to-one relationship between Report and Transcript, which table should hold the foreign key?
**Answer:** Either can, but typically the "dependent" entity (Transcript) holds the foreign key pointing to the "owner" (Report), since transcripts depend on reports existing first.

### Performance Considerations
**Question:** What's the performance impact of always fetching both metadata and transcript when only one is needed?
**Answer:** Wastes API quota, adds unnecessary latency (transcript fetching is slower), increases memory usage, and creates overhead that scales poorly with traffic.

---

## Study Notes

- Foreign key relationships enforce database integrity - can't save a Transcript with an invalid report_id
- JPA's `@Column(columnDefinition = "TEXT")` is used for large text fields that exceed VARCHAR limits
- Repository query methods like `findByVideoId(String videoId)` are auto-implemented by Spring Data JPA using method naming conventions
- One-to-one relationships enable bidirectional navigation: `transcript.getReport()` works if properly configured
