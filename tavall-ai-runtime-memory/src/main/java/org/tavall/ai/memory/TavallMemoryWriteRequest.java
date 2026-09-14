package org.tavall.ai.memory;

import java.util.List;
import java.util.Map;

/** Explicit durable-memory write request. Write authority is not caller-selectable. */
public record TavallMemoryWriteRequest(
        TavallMemoryScope scope,
        TavallMemoryKind kind,
        String title,
        String summary,
        List<String> facts,
        Integer importance,
        String sensitivity,
        String sourceReference,
        String supersedesMemoryId,
        Map<String, Object> metadata
) {
    public TavallMemoryWriteRequest {
        facts = facts == null ? List.of() : facts.stream()
                .filter(value -> value != null && !value.isBlank())
                .map(String::strip)
                .toList();
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }
}
