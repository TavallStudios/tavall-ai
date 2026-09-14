package org.tavall.ai.memory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * One canonical durable-memory transaction. Implementations must keep record mutation and semantic
 * outbox mutation atomic and must hold stable-identity locks until commit/rollback.
 */
public interface TavallMemoryTransaction {
    void lockStableIdentities(List<TavallMemoryStableIdentity> identities);

    Optional<TavallMemoryRecord> findActive(TavallMemoryStableIdentity identity);

    TavallMemoryRecord requireById(String memoryId);

    TavallMemoryRecord create(
            TavallMemoryIdentity identity,
            TavallMemoryStableIdentity stableIdentity,
            TavallMemoryWriteRequest request,
            String title,
            String titleKey,
            int importance,
            String sensitivity,
            String writeAuthority,
            String sourceEventId,
            Map<String, Object> metadata
    );

    TavallMemoryRecord update(
            TavallMemoryRecord existing,
            TavallMemoryWriteRequest request,
            String title,
            int importance,
            String sensitivity,
            String writeAuthority,
            List<String> sourceEventIds,
            Map<String, Object> metadata
    );

    void supersede(String memoryId, String supersededByMemoryId);

    void enqueueSemanticUpsert(TavallMemoryRecord record, String semanticNamespace, String sourceReference);

    void enqueueSemanticDelete(TavallMemoryRecord record, String semanticNamespace);
}
