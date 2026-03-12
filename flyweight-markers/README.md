Flyweight — Deduplicate Map Marker Styles (Refactoring)
------------------------------------------------------
Narrative (Current Code)
A CLI tool called **GeoDash** renders a large list of map markers (pins).
Right now, every `MapMarker` stores its own style fields (shape, color, size, filled).
When rendering thousands of markers, we end up creating thousands of duplicate style objects → memory blow-up.

Your Task
1) Extract an immutable `MarkerStyle` (shape, color, size, filled) as **intrinsic state**.
2) Implement `MarkerStyleFactory` that caches and returns shared `MarkerStyle` instances by key.
3) Modify `MapMarker` to hold:
   - `MarkerStyle` (intrinsic)
   - marker-specific fields (extrinsic): `lat`, `lng`, `label`
4) Update `MapDataSource` (marker creation pipeline) to obtain styles via the factory
   (no `new MarkerStyle(...)` during marker creation).

Acceptance Criteria
- Same rendering “cost” as before (same number of markers rendered, same output format).
- Identical style configurations reuse the same `MarkerStyle` instance
  (see `QuickCheck` — it should report a small number of unique styles).
- `MarkerStyle` is immutable (all fields final, no setters).
- `MapMarker` stores only extrinsic state plus a reference to shared `MarkerStyle`.

Hints
- Use a `Map<String, MarkerStyle>` cache in the factory.
- Key suggestion: `"PIN|RED|12|F"` (shape|color|size|filledFlag)

Build & Run
  cd flyweight-markers/src
  javac com/example/map/*.java
  java com.example.map.App

Repo intent
This is a **refactoring assignment**: the starter code is intentionally wasteful.
Students should refactor to Flyweight without changing the external behavior.

---


**Step 1 — State the problem:**
"We have a map application that renders 30,000 markers. Each marker has location data (lat, lng, label) and a style (shape, color, size, filled). In the original code, every marker creates its own MarkerStyle object. With 30,000 markers, that's 30,000 style objects in memory — but there are only 96 unique combinations (3 shapes × 4 colors × 4 sizes × 2 filled/outline)."

**Step 2 — Explain Flyweight concept:**
"Flyweight separates intrinsic state (shared, doesn't change per object) from extrinsic state (unique per object). Here, the style is intrinsic — many markers share the same 'RED PIN size 12 filled'. The location and label are extrinsic — unique to each marker."

**Step 3 — Show MarkerStyle.java:**
"MarkerStyle is now immutable — all fields are final, no setters. It's safe to share because nobody can modify it."

**Step 4 — Show MarkerStyleFactory.java:**
"The factory uses a HashMap with a string key like 'PIN|RED|12|F'. When you ask for a style, it checks the cache first. If the key exists, it returns the cached instance. If not, it creates one, puts it in the cache, and returns it. So identical styles always return the same object."

**Step 5 — Show MapDataSource.java:**
"Instead of 'new MarkerStyle(...)' inside each marker, MapDataSource calls 'styleFactory.get(shape, color, size, filled)'. The factory decides whether to create or reuse."

**Step 6 — Show MapMarker.java:**
"MapMarker's constructor now takes a MarkerStyle directly instead of raw fields. It just stores the reference — no object creation."

**Step 7 — Run QuickCheck and prove it:**
"QuickCheck creates 20,000 markers and counts unique style instances by identity hash code. Before: 20,000 objects. After: 96. We saved 19,904 objects worth of memory."