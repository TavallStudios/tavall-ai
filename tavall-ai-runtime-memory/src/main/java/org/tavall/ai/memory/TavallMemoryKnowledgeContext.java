package org.tavall.ai.memory;

import java.util.List;
import java.util.Map;

/** Structural/temporal/external knowledge context returned by Graphify, Graphiti, or future providers. */
public record TavallMemoryKnowledgeContext(
        String providerId,
        String role,
        String summary,
        List<String> evidence,
        Map<String, Object> metadata,
        boolean degraded,
        String error
) {
    public TavallMemoryKnowledgeContext {
        evidence = evidence == null ? List.of() : List.copyOf(evidence);
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
        error = error == null ? "" : error;
    }
}
