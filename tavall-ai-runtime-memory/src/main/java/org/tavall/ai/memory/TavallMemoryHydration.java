package org.tavall.ai.memory;

import java.util.List;

/** Structured provider-neutral memory context. Prompt/provider projection happens outside this module. */
public record TavallMemoryHydration(
        List<TavallMemoryRecord> exactRecords,
        List<TavallMemorySemanticMatch> semanticMatches,
        List<TavallMemoryKnowledgeContext> knowledgeContexts
) {
    public TavallMemoryHydration {
        exactRecords = exactRecords == null ? List.of() : List.copyOf(exactRecords);
        semanticMatches = semanticMatches == null ? List.of() : List.copyOf(semanticMatches);
        knowledgeContexts = knowledgeContexts == null ? List.of() : List.copyOf(knowledgeContexts);
    }
}
