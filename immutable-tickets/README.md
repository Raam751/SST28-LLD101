Exercise B — Immutable Classes (Incident Tickets)
------------------------------------------------
Narrative
A small CLI tool called **HelpLite** creates and manages support/incident tickets.
Today, `IncidentTicket` is **mutable**:
- multiple constructors
- public setters
- validation scattered across the codebase
- objects can be modified after being "created", causing audit/log inconsistencies

Refactor the design so `IncidentTicket` becomes **immutable** and is created using a **Builder**.

What you have (Starter)
- `IncidentTicket` has public setters + several constructors.
- `TicketService` creates a ticket, then mutates it later (bad).
- Validation is duplicated and scattered, making it easy to miss checks.
- `TryIt` demonstrates how the same object can change unexpectedly.

Tasks
1) Refactor `IncidentTicket` to an **immutable class**
   - private final fields
   - no setters
   - defensive copying for collections
   - safe getters (no internal state leakage)

2) Introduce `IncidentTicket.Builder`
   - Required: `id`, `reporterEmail`, `title`
   - Optional: `description`, `priority`, `tags`, `assigneeEmail`, `customerVisible`, `slaMinutes`, `source`
   - Builder should be fluent (`builder().id(...).title(...).build()`)

3) Centralize validation
   - Move ALL validation to `Builder.build()`
   - Use helpers in `Validation.java` (add more if needed)
   - Examples:
     - id: non-empty, length <= 20, only [A-Z0-9-] (you can reuse helper)
     - reporterEmail/assigneeEmail: must look like an email
     - title: non-empty, length <= 80
     - priority: one of LOW/MEDIUM/HIGH/CRITICAL
     - slaMinutes: if provided, must be between 5 and 7,200

4) Update `TicketService`
   - Stop mutating a ticket after creation
   - Any “updates” should create a **new** ticket instance (e.g., by Builder copy/from method)
   - Keep the API simple; you can add `toBuilder()` or `Builder.from(existing)`

Acceptance
- `IncidentTicket` has no public setters and fields are final.
- Tickets cannot be modified after creation (including tags list).
- Validation happens only in one place (`build()`).
- `TryIt` still works, but now demonstrates immutability (attempted mutations should not compile or have no effect).
- Code compiles and runs with the starter commands below.

Build/Run (Starter demo)
  cd immutable-tickets/src
  javac com/example/tickets/*.java TryIt.java
  java TryIt

Tip
After refactor, you can update `TryIt` to show:
- building a ticket
- “updating” by creating a new instance
- tags list is not mutable from outside

---



**Step 1 — State the problem:**
"IncidentTicket was fully mutable — it had 10 public setters, multiple constructors, and the tags list leaked through the getter. Anyone could change any field at any time after creation. This causes audit inconsistencies — you log a ticket as MEDIUM priority, someone mutates it to CRITICAL, and your log is now wrong."

**Step 2 — Show the immutability fix (open IncidentTicket.java):**
"All fields are now private final. There are zero setters. The constructor is private — only the Builder can create instances. The tags list is wrapped in Collections.unmodifiableList() with a defensive copy, so even if you get the list through getTags(), you can't add or remove from it."

**Step 3 — Show the Builder pattern:**
"Since the constructor is private and takes many parameters, I used a Builder. Required fields — id, reporterEmail, title — go in the Builder constructor. Optional fields use fluent setter methods that return the builder itself: .priority('HIGH').source('EMAIL').addTag('NETWORK'). Finally, build() validates everything and creates the immutable object."

**Step 4 — Show centralized validation:**
"All validation happens in one place — the build() method. It calls Validation.requireTicketId() for the ID format, Validation.requireEmail() for emails, requireMaxLen() for title length, requireOneOf() for priority values, and requireRange() for SLA minutes. No validation is scattered across the codebase anymore."

**Step 5 — Show toBuilder() and TicketService:**
"Since the object is immutable, you can't mutate it after creation. For 'updates', there's a toBuilder() method that copies all fields into a new Builder. TicketService.escalateToCritical() calls t.toBuilder().priority('CRITICAL').addTag('ESCALATED').build() — it returns a new ticket, the original is untouched."

**Step 6 — Run TryIt and prove immutability:**
"In the output, after escalation, the original ticket still shows MEDIUM priority. And when we try tags.add('HACKED'), we get UnsupportedOperationException — the list is truly immutable."