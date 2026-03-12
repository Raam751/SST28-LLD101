# Ex9 — DIP: Assignment Evaluation Pipeline

## 1. Context
An evaluation pipeline checks submissions using a rubric, runs plagiarism checks, grades code, and writes a report.

## 2. Current behavior
- `EvaluationPipeline.evaluate` directly instantiates concrete graders/checkers/writers with `new`
- Prints a final summary line and “writes” a report

## 3. What’s wrong (at least 5 issues)
1. High-level pipeline depends on low-level concrete classes (hard-coded `new` everywhere).
2. Hard to test pipeline without running real checks.
3. Changing a component requires editing pipeline code.
4. No clear abstraction boundaries; responsibilities are mixed.
5. Configuration is embedded (paths, thresholds).

## 4. Your task
Checkpoint A: Run and capture output.
Checkpoint B: Introduce small abstractions for grader/checker/writer.
Checkpoint C: Inject dependencies into pipeline.
Checkpoint D: Keep output identical.

## 5. Constraints
- Preserve output and line order.
- Keep `Submission` fields unchanged.
- No external libraries.

## 6. Acceptance criteria
- Pipeline depends on abstractions, not concretes.
- Easy to substitute test doubles.

## 7. How to run
```bash
cd SOLID/Ex9/src
javac *.java
java Main
```

## 8. Sample output
```text
=== Evaluation Pipeline ===
PlagiarismScore=12
CodeScore=78
Report written: report-23BCS1007.txt
FINAL: PASS (total=90)
```

## 9. Hints (OOP-only)
- Define minimal interfaces with only what the pipeline needs.
- Pass dependencies via constructor.

## 10. Stretch goals
- Add a second grading strategy without editing pipeline logic.

---


**Step 1 — State the problem:**
"This is about DIP — Dependency Inversion Principle. We had an EvaluationPipeline that directly created concrete classes with `new` inside its evaluate() method — `new PlagiarismChecker()`, `new CodeGrader()`, `new ReportWriter()`. The high-level pipeline was tightly coupled to low-level implementations."

**Step 2 — Explain why this is bad:**
"If I wanted to swap the plagiarism checker for a better one, or use a mock for testing, I'd have to edit the pipeline itself. The high-level business logic is wired to specific implementations — you can't change one without touching the other."

**Step 3 — Show the fix (open EvaluationPipeline.java):**
"I created three interfaces: Checker, Grader, and ReportWriter. The pipeline now accepts them through its constructor. It has no idea which concrete classes are behind these interfaces — it just calls checker.check(), grader.grade(), and writer.write()."

**Step 4 — Show Demo09.java (the Composition Root):**
"In Demo09, we create the concrete implementations and inject them: `new EvaluationPipeline(new PlagiarismChecker(), new CodeGrader(), new ReportWriter())`. This is the only place in the code that knows about concrete classes. The pipeline is completely decoupled."

**Step 5 — Key takeaway:**
"DIP says high-level modules should not depend on low-level modules — both should depend on abstractions. By injecting interfaces through the constructor, the pipeline doesn't know or care which implementations it's using. You can swap any component without touching the pipeline code."
