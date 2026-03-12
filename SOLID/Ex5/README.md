# Ex5 — LSP: File Exporter Hierarchy

## 1. Context
A reporting tool exports student performance data to multiple formats.

## 2. Current behavior
- `Exporter` has `export(ExportRequest)` that returns `ExportResult`
- `PdfExporter` throws for large content (tightens preconditions)
- `CsvExporter` silently changes meaning by dropping newlines and commas poorly
- `JsonExporter` returns empty on null (inconsistent contract)
- `Main` demonstrates current behavior

## 3. What’s wrong (at least 5 issues)
1. Subclasses violate expectations of the base `Exporter` contract.
2. `PdfExporter` throws for valid requests (from base perspective).
3. `CsvExporter` changes semantics of fields (data corruption risk).
4. `JsonExporter` handles null differently than others.
5. Callers cannot rely on substitutability; they need format-specific workarounds.
6. Contract is not documented; behavior surprises are runtime.

## 4. Your task
Checkpoint A: Run and capture output.
Checkpoint B: Define a clear base contract (preconditions/postconditions).
Checkpoint C: Refactor hierarchy so all exporters honor the same contract.
Checkpoint D: Keep observable outputs identical for current inputs.

## 5. Constraints
- Keep `Main` outputs unchanged for the given samples.
- No external libraries.
- Default package.

## 6. Acceptance criteria
- Base contract is explicit and enforced consistently.
- No exporter tightens preconditions compared to base contract.
- Caller should not need `instanceof` to be safe.

## 7. How to run
```bash
cd SOLID/Ex5/src
javac *.java
java Main
```

## 8. Sample output
```text
=== Export Demo ===
PDF: ERROR: PDF cannot handle content > 20 chars
CSV: OK bytes=42
JSON: OK bytes=61
```

## 9. Hints (OOP-only)
- If a subtype cannot support the base contract, reconsider inheritance.
- Prefer composition: separate “format encoding” from “delivery constraints”.

## 10. Stretch goals
- Add a new exporter without changing existing exporters.

---

**Step 1 — State the problem:**
"This exercise is about LSP — Liskov Substitution Principle. We have an exporter hierarchy where a base Exporter class has subclasses for PDF, CSV, JSON, and XML. The problem was that each subclass behaved differently — PdfExporter threw exceptions for large content, CsvExporter silently corrupted data, and JsonExporter handled null differently. You couldn't safely swap one for another."

**Step 2 — Explain the violation:**
"LSP says if you replace a parent with any child, the program should still work correctly. Here, PdfExporter tightened the preconditions by rejecting large content that the base class would have accepted. That's an LSP violation — the subclass is more restrictive than the parent."

**Step 3 — Show the fix (open Exporter.java):**
"I used the Template Method pattern. The `export()` method in the base class is `final` — no subclass can override it. It handles all the common stuff: null checks, blank title validation, and running any format-specific constraints. Subclasses only override `encode()`, which does pure format conversion."

**Step 4 — Show DeliveryConstraint (open Demo05.java):**
"The PDF's 20-char limit was moved OUT of PdfExporter and into a pluggable DeliveryConstraint. In Demo05, we attach the constraint externally: `pdf.setConstraint(r -> ...)`. This way PdfExporter itself doesn't break LSP — the restriction is optional and configurable."

**Step 5 — Show a subclass (open PdfExporter.java):**
"See how simple the subclass is now? Just 3 lines of actual code. It only does encoding — no validation, no exceptions, no special cases. All exporters follow the same contract."

**Step 6 — Key takeaway:**
"By locking the algorithm in the base class and letting subclasses only handle encoding, we guarantee LSP. Any exporter can replace any other without surprises."
