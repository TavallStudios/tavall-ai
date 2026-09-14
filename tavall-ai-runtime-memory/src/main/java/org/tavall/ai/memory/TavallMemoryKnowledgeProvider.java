package org.tavall.ai.memory;

import java.util.Map;

/** Optional external knowledge provider such as Graphify or Graphiti. */
public interface TavallMemoryKnowledgeProvider {
    String id();

    TavallMemoryKnowledgeContext retrieve(
            TavallMemoryIdentity identity,
            String repoPath,
            String query,
            int limit,
            Map<String, Object> metadata
    );
}
