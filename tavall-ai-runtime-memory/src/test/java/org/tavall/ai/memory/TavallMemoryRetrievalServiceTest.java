package org.tavall.ai.memory;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TavallMemoryRetrievalServiceTest {
    @Test
    void cachesExactStateByGlobalRevisionAndRanksSemanticCandidates() {
        TavallMemoryIdentity identity = new TavallMemoryIdentity("user", "workspace", "project", "chat", "session", "thread");
        RecordingHotState hot = new RecordingHotState();
        TavallMemoryRecordStore records = new TavallMemoryRecordStore() {
            @Override
            public <T> T transact(Function<TavallMemoryTransaction, T> work) {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<TavallMemoryRecord> loadExactState(TavallMemoryIdentity ignored, int limit) {
                return List.of();
            }
        };
        TavallMemorySemanticStore semantic = (ignored, query, limit, required) -> List.of(
                new TavallMemorySemanticMatch("older", 0.80, Map.of("importance", 20, "scope", "GLOBAL")),
                new TavallMemorySemanticMatch("stronger", 0.90, Map.of("importance", 90, "scope", "SESSION"))
        );
        TavallMemoryKnowledgeProvider degraded = new TavallMemoryKnowledgeProvider() {
            @Override
            public String id() {
                return "graphiti";
            }

            @Override
            public TavallMemoryKnowledgeContext retrieve(
                    TavallMemoryIdentity ignored,
                    String repoPath,
                    String query,
                    int limit,
                    Map<String, Object> metadata
            ) {
                throw new IllegalStateException("provider unavailable");
            }
        };

        TavallMemoryRetrievalService service = new TavallMemoryRetrievalService(
                records,
                hot,
                semantic,
                List.of(degraded),
                new TavallMemoryRuntimePolicy(10, 5, 5, Duration.ofMinutes(5))
        );

        TavallMemoryHydration hydration = service.hydrate(identity, "/repo", "memory query", Map.of());

        assertEquals("stronger", hydration.semanticMatches().getFirst().id());
        assertEquals(1, hydration.knowledgeContexts().size());
        assertTrue(hydration.knowledgeContexts().getFirst().degraded());
        assertTrue(hot.lastStoredKey.endsWith("global-revision=7"));
    }

    @Test
    void semanticFailureDoesNotDiscardExactState() {
        TavallMemoryIdentity identity = new TavallMemoryIdentity("user", "workspace", "project", "", "", "");
        RecordingHotState hot = new RecordingHotState();
        TavallMemoryRecord exact = TavallMemoryWriteServiceTestRecordFactory.record(identity);
        TavallMemoryRecordStore records = new TavallMemoryRecordStore() {
            @Override
            public <T> T transact(Function<TavallMemoryTransaction, T> work) {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<TavallMemoryRecord> loadExactState(TavallMemoryIdentity ignored, int limit) {
                return List.of(exact);
            }
        };
        TavallMemorySemanticStore semantic = (ignored, query, limit, required) -> {
            throw new IllegalStateException("qdrant unavailable");
        };
        TavallMemoryRetrievalService service = new TavallMemoryRetrievalService(
                records,
                hot,
                semantic,
                List.of(),
                new TavallMemoryRuntimePolicy(10, 5, 5, Duration.ofMinutes(5))
        );

        TavallMemoryHydration hydration = service.hydrate(identity, "", "query", Map.of());

        assertEquals(List.of(exact), hydration.exactRecords());
        assertEquals(List.of(), hydration.semanticMatches());
        assertEquals("semantic", hydration.knowledgeContexts().getFirst().providerId());
        assertTrue(hydration.knowledgeContexts().getFirst().degraded());
    }

    private static final class RecordingHotState implements TavallMemoryHotStateStore {
        private String lastStoredKey = "";

        @Override
        public long globalRevision(String authorityKey) {
            return 7;
        }

        @Override
        public long advanceGlobalRevision(String authorityKey) {
            return 8;
        }

        @Override
        public Optional<List<TavallMemoryRecord>> loadExactState(String cacheKey) {
            return Optional.empty();
        }

        @Override
        public void storeExactState(String cacheKey, List<TavallMemoryRecord> records, Duration ttl) {
            lastStoredKey = cacheKey;
        }
    }

    private static final class TavallMemoryWriteServiceTestRecordFactory {
        private static TavallMemoryRecord record(TavallMemoryIdentity identity) {
            return new TavallMemoryRecord(
                    "mem-exact", identity.userId(), identity.workspaceId(), "", "", identity.projectId(), "",
                    TavallMemoryScope.PROJECT, TavallMemoryKind.PROJECT_STATE, "state", "state", "summary",
                    List.of(), List.of(), 1, "active", 80, "internal", "explicit", "", false,
                    Map.of(), null, null
            );
        }
    }
}
