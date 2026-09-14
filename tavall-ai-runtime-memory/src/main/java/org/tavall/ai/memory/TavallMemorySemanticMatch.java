package org.tavall.ai.memory;

import java.util.Map;

public record TavallMemorySemanticMatch(
        String id,
        double score,
        Map<String, Object> metadata
) {
    public TavallMemorySemanticMatch {
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }
}
