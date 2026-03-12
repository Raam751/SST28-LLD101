package com.example.metrics;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

/**
 * INTENTION: Global metrics registry (should be a Singleton).
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - Constructor is public -> anyone can create instances.
 * - getInstance() is lazy but NOT thread-safe -> can create multiple instances.
 * - Reflection can call the constructor to create more instances.
 * - Serialization can create a new instance when deserialized.
 *
 * TODO (student):
 * 1) Make it a proper lazy, thread-safe singleton (private ctor)
 * 2) Block reflection-based multiple construction
 * 3) Preserve singleton on serialization (readResolve)
 */
public class MetricsRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static volatile MetricsRegistry INSTANCE;
    private final Map<String, Long> counters = new HashMap<>();

    // private constructor + reflection guard
    private MetricsRegistry() {
        if (INSTANCE != null) {
            throw new RuntimeException("Singleton! Use getInstance() instead.");
        }
    }

    // double-checked locking — thread-safe lazy init
    public static MetricsRegistry getInstance() {
        if (INSTANCE == null) { // 1st check (no lock, fast path)
            synchronized (MetricsRegistry.class) { // lock only if null
                if (INSTANCE == null) { // 2nd check inside lock
                    INSTANCE = new MetricsRegistry();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void setCount(String key, long value) {
        counters.put(key, value);
    }

    public synchronized void increment(String key) {
        counters.put(key, getCount(key) + 1);
    }

    public synchronized long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    public synchronized Map<String, Long> getAll() {
        return Collections.unmodifiableMap(new HashMap<>(counters));
    }

    // returning the singleton instead of a new object
    @Serial
    private Object readResolve() {
        return getInstance();
    }
}