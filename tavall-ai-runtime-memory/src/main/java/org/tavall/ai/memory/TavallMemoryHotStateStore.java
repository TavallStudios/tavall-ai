package org.tavall.ai.memory;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/** Hot exact-state cache/revision boundary, normally backed by Redis. */
public interface TavallMemoryHotStateStore {
    long globalRevision(String authorityKey);

    long advanceGlobalRevision(String authorityKey);

    Optional<List<TavallMemoryRecord>> loadExactState(String cacheKey);

    void storeExactState(String cacheKey, List<TavallMemoryRecord> records, Duration ttl);
}
