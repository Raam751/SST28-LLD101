Proxy — Secure & Lazy-Load Reports (Refactoring)
------------------------------------------------
Narrative (Current Code)
A small CLI tool called CampusVault opens internal reports for different users.
Right now, ReportViewer talks directly to ReportFile and eagerly loads the report content every time.

Problems in the current design:
- No access control: any user can open any report.
- No lazy loading: expensive file loading happens immediately on each open.
- No caching: the same report may be loaded multiple times unnecessarily.
- Clients depend directly on the concrete implementation.

Your Task
1) Introduce a Report abstraction.
2) Keep the expensive file-reading logic inside a real subject (for example, RealReport).
3) Add a ReportProxy that:
   - checks whether the user is allowed to access the report
   - lazy-loads the real report only when needed
   - reuses the loaded real report for repeated views through the same proxy
4) Update ReportViewer / App so clients use the proxy instead of directly using the concrete file loader.

Acceptance Criteria
- Unauthorized users cannot view restricted reports.
- Real report loading happens only when access is granted.
- Real report content is loaded lazily (not during proxy construction).
- Repeated views of the same report through the same proxy should not reload the file every time.
- Output remains easy to verify from console logs.

Hints
- Define an interface: Report { void display(User user); }
- Let RealReport do the expensive load.
- Let ReportProxy hold metadata + a nullable RealReport reference.
- Add logs so it is obvious whether a report was really loaded.

Build & Run
  cd proxy-reports/src
  javac com/example/reports/*.java
  java com.example.reports.App

Repo intent
This is a refactoring assignment: the starter code works, but it does not use Proxy properly.
Students should refactor the design so access control + lazy loading happen via a proxy.

---


**Step 1 — State the problem:**
"CampusVault is a tool that opens internal reports. The original code had three problems: no access control — any user could open any report; no lazy loading — the expensive file reading happened immediately every time; and no caching — the same report could be loaded multiple times."

**Step 2 — Explain the Proxy concept:**
"Think of a library front desk. You don't walk into the back room and grab rare books yourself. The front desk checks your ID, fetches the book for you, and if you ask for the same book again, they already have it at the desk. That's a Proxy — it sits between you and the real thing."

**Step 3 — Show Report interface:**
"Report is a simple interface: void display(User user). Both RealReport and ReportProxy implement it. The client doesn't know which one it's talking to."

**Step 4 — Show RealReport.java:**
"RealReport does the expensive work — the file loading happens in its constructor with a simulated 120ms delay. Once loaded, display() just prints the content."

**Step 5 — Show ReportProxy.java:**
"ReportProxy is the heart of the pattern. It does three things in display(): First, it checks access using AccessControl — if the user's role doesn't match the report classification, it prints ACCESS DENIED and returns immediately, never loading the report. Second, if access is granted and cachedReport is null, it creates a RealReport — this is the lazy loading. Third, it caches the RealReport so the next call skips loading."

**Step 6 — Show the output proof:**
"In the output, when student Jasleen tries to open a FACULTY report, we see ACCESS DENIED — no disk loading happened. When admin Kshitij opens the budget audit twice, the disk loading message appears only once — the second time it's served from cache."

**Step 7 — Key takeaway:**
"The Proxy pattern lets you add cross-cutting concerns — access control, lazy loading, caching — without touching the real object. ReportViewer doesn't even know it's talking to a proxy."