package org.tavall.ai.memory;

import java.util.List;
import java.util.Map;

/** Semantic retrieval boundary; durable semantic writes are enqueued through the record transaction. */
public interface TavallMemorySemanticStore {
    List<TavallMemorySemanticMatch> search(
            TavallMemoryIdentity identity,
            String query,
            int limit,
            Map<String, Object> requiredMetadata
    );
}
