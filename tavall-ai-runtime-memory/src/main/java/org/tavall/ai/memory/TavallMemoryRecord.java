package org.tavall.ai.memory;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

/** Canonical durable memory record returned by the storage authority. */
public record TavallMemoryRecord(
        String memoryId,
        String userId,
        String workspaceId,
        String sessionId,
        String chatId,
        String projectId,
        String threadKey,
        TavallMemoryScope scope,
        TavallMemoryKind kind,
        String title,
        String titleKey,
        String summary,
        List<String> facts,
        List<String> sourceEventIds,
        int version,
        String status,
        int importance,
        String sensitivity,
        String writeAuthority,
        String supersededBy,
        boolean tombstoned,
        Map<String, Object> metadata,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public TavallMemoryRecord {
        facts = facts == null ? List.of() : List.copyOf(facts);
        sourceEventIds = sourceEventIds == null ? List.of() : List.copyOf(sourceEventIds);
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }

    public boolean active() {
        return "active".equals(status) && !tombstoned && TavallMemoryIdentity.clean(supersededBy).isBlank();
    }
}
