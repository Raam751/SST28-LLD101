# Ex8 — ISP: Student Club Management Admin Tools

## 1. Context
Clubs have different roles: treasurer, secretary, event lead. The admin tool interface currently combines everything.

## 2. Current behavior
- One interface `ClubAdminTools` includes finance, minutes, and event operations
- Each role tool implements methods it does not use (dummy / exceptions)
- `ClubConsole` calls only the relevant subset per role

## 3. What’s wrong (at least 5 issues)
1. Fat interface forces irrelevant methods.
2. Dummy implementations cause hidden failures later.
3. Clients depend on methods they don’t need.
4. New role tools become harder to implement safely.
5. Capabilities are unclear.

## 4. Your task
- Split interface into smaller role/capability interfaces.
- Ensure each tool depends only on the methods it uses.
- Preserve output.

## 5. Constraints
- Preserve output and command order.
- Keep class names unchanged.

## 6. Acceptance criteria
- No dummy/no-op implementations for irrelevant methods.
- `ClubConsole` depends on minimal interfaces.

## 7. How to run
```bash
cd SOLID/Ex8/src
javac *.java
java Main
```

## 8. Sample output
```text
=== Club Admin ===
Ledger: +5000 (sponsor)
Minutes added: "Meeting at 5pm"
Event created: HackNight (budget=2000)
Summary: ledgerBalance=5000, minutes=1, events=1
```

## 9. Hints (OOP-only)
- Identify client groups: finance client, minutes client, events client.
- Split by what callers actually need.

## 10. Stretch goals
- Add “publicity lead” without implementing finance methods.

---


**Step 1 — State the problem:**
"This is another ISP exercise. We had a student club management system where one fat interface called ClubAdminTools had methods for finances, minutes, and events. The Treasurer had to implement addMinutes() and createEvent() even though they only handle money. The Secretary had to implement addLedgerEntry() even though they only write minutes."

**Step 2 — Show the impact:**
"These dummy implementations are dangerous. If someone accidentally calls treasurer.addMinutes(), it either does nothing silently or throws an exception. Both are bad — silent failure hides bugs, and exceptions crash the app."

**Step 3 — Show the fix:**
"I split ClubAdminTools into three focused interfaces: FinanceOps (addLedgerEntry, getBalance), MinuteOps (addMinutes, getMinutesCount), and EventOps (createEvent, getEventCount). Each role tool implements only the interfaces relevant to its role."

**Step 4 — Show a concrete tool:**
"TreasurerTool implements only FinanceOps. SecretaryTool implements only MinuteOps. EventLeadTool implements only EventOps. No dummy methods anywhere."

**Step 5 — Show ClubConsole:**
"ClubConsole now accepts the specific interface it needs for each operation. When it needs to add a ledger entry, it asks for FinanceOps. When it needs to add minutes, it asks for MinuteOps. It never depends on methods it doesn't use."

**Step 6 — Key takeaway:**
"ISP is about matching interfaces to client needs. By splitting the fat interface into role-specific ones, each client depends only on what it actually uses, and each implementation only provides what it can actually do."
