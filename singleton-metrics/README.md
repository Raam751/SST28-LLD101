Exercise A — Singleton Refactoring (Metrics Registry)
----------------------------------------------------
Narrative
A CLI tool called **PulseMeter** collects runtime metrics (counters) and exposes them globally
so any part of the app can increment counters like `REQUESTS_TOTAL`, `DB_ERRORS`, etc.

The current implementation is **not a real singleton**, **not thread-safe**, and is vulnerable to
**reflection** and **serialization** breaking the singleton guarantee.

Your job is to refactor it into a **proper, thread-safe, lazy-initialized Singleton**.

What you have (Starter)
- `MetricsRegistry` is *intended* to be global, but:
  - `getInstance()` can return different objects under concurrency.
  - The constructor is not private.
  - Reflection can create multiple instances.
  - Serialization/deserialization can produce a new instance.
- `MetricsLoader` incorrectly uses `new MetricsRegistry()`.

Tasks
1) Make `MetricsRegistry` a proper, **thread-safe singleton**
   - **Lazy initialization**
   - **Private constructor**
   - Thread safety: pick one approach (recommended: static holder or double-checked locking)

2) Block reflection-based multiple construction
   - If the constructor is called when an instance already exists, throw an exception
   - (Hint: use a static flag/instance check inside the constructor)

3) Preserve singleton on serialization
   - Implement `readResolve()` so deserialization returns the same singleton instance

4) Update `MetricsLoader` to use the singleton
   - No `new MetricsRegistry()` anywhere in code

Acceptance
- Single instance across threads within a JVM run.
- Reflection cannot construct a second instance.
- Deserialization returns the same instance.
- Loading metrics from `metrics.properties` works.
- Values are accessible via:
  - `increment(key)`
  - `getCount(key)`
  - `getAll()`

Build/Run (Starter)
  cd singleton-metrics/src
  javac com/example/metrics/*.java
  java com.example.metrics.App

Useful Demo Commands (after you fix it)
- Concurrency check:
  java com.example.metrics.ConcurrencyCheck
- Reflection attack check:
  java com.example.metrics.ReflectionAttack
- Serialization check:
  java com.example.metrics.SerializationCheck

Note
This starter is intentionally broken. Some of these checks will "succeed" in breaking the singleton
until you fix the implementation.

---

**Step 1 — State the problem:**
"MetricsRegistry was supposed to be a global singleton — one shared counter store for the whole app. But it had four problems: the constructor was public so anyone could call new, getInstance() was not thread-safe so two threads could create two instances, reflection could bypass the private constructor, and serialization could produce a second object."

**Step 2 — Explain double-checked locking (open MetricsRegistry.java):**
"I used double-checked locking for thread safety. The INSTANCE field is marked volatile so all threads see the latest value. In getInstance(), there's a first null-check without a lock — this is the fast path, no synchronization cost for 99% of calls. Only if it's null do we enter a synchronized block. Inside the block, we check null again because another thread might have created it while we were waiting for the lock."

**Step 3 — Show the reflection guard:**
"Inside the private constructor, there's a check: if INSTANCE is already set, we throw a RuntimeException. So even if someone uses reflection to call the constructor, they'll get an exception instead of a second instance."

**Step 4 — Show readResolve():**
"When Java deserializes an object, it normally creates a brand new instance. The readResolve() method intercepts that — it tells Java to return the existing singleton instead of the deserialized copy. So serialization can't break the singleton either."

**Step 5 — Show MetricsLoader.java:**
"MetricsLoader was using new MetricsRegistry() directly. I replaced it with MetricsRegistry.getInstance(). Now both App and MetricsLoader use the same instance — you can see in the output that the identity hash codes match."

**Step 6 — Run the checks:**
"ConcurrencyCheck creates 80 threads racing to call getInstance() — only 1 unique instance. ReflectionAttack tries to call the constructor via reflection — gets a RuntimeException. SerializationCheck serializes and deserializes — same object, Same object? true."