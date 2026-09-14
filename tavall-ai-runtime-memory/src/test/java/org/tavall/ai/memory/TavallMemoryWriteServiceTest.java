package org.tavall.ai.memory;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TavallMemoryWriteServiceTest {
    @Test
    void explicitWriteAuthorityAndOutboxCommitBeforeCacheRefresh() {
        FakeRecordStore store = new FakeRecordStore();
        FakeHotState hot = new FakeHotState(store);
        TavallMemoryWriteService service = new TavallMemoryWriteService(store, hot, policy());

        TavallMemoryRecord record = service.record(identity("project-a"), new TavallMemoryWriteRequest(
                TavallMemoryScope.PROJECT,
                TavallMemoryKind.PREFERENCE,
                "Architecture preference",
                "Prefer one canonical capability authority.",
                List.of("Do not duplicate MCP surfaces."),
                90,
                "internal",
                "test://source",
                "",
                Map.of("writeMode", "caller-controlled")
        ));

        assertEquals(TavallMemoryWriteService.EXPLICIT_WRITE_AUTHORITY, record.writeAuthority());
        assertEquals("explicit", record.metadata().get("writeMode"));
        assertTrue(store.transaction.upserted);
        assertTrue(hot.cacheObservedCommittedTransaction);
    }

    @Test
    void projectSupersessionCannotCrossProjectAuthority() {
        FakeRecordStore store = new FakeRecordStore();
        store.transaction.byId.put("mem-old", record("mem-old", "project-a", TavallMemoryScope.PROJECT, "old-key", "project-a"));
        TavallMemoryWriteService service = new TavallMemoryWriteService(store, new FakeHotState(store), policy());

        assertThrows(IllegalArgumentException.class, () -> service.record(
                identity("project-b"),
                request(TavallMemoryScope.PROJECT, "Replacement", "mem-old")
        ));
        assertEquals(0, store.transaction.creates);
    }

    @Test
    void globalSupersessionUsesOldSemanticNamespaceAndLocksDeterministically() {
        FakeRecordStore store = new FakeRecordStore();
        TavallMemoryRecord old = record("mem-old", "", TavallMemoryScope.GLOBAL, "old-key", "project-a");
        store.transaction.byId.put(old.memoryId(), old);
        TavallMemoryWriteService service = new TavallMemoryWriteService(store, new FakeHotState(store), policy());

        service.record(identity("project-b"), request(TavallMemoryScope.GLOBAL, "Replacement", old.memoryId()));

        assertEquals("project-a", store.transaction.deletedNamespace);
        assertEquals(store.transaction.locked.stream().sorted().toList(), store.transaction.locked);
        assertEquals(1, store.transaction.creates);
    }

    @Test
    void globalWriteAdvancesAuthorityRevisionAfterCommit() {
        FakeRecordStore store = new FakeRecordStore();
        FakeHotState hot = new FakeHotState(store);
        TavallMemoryWriteService service = new TavallMemoryWriteService(store, hot, policy());

        service.record(identity("project-a"), request(TavallMemoryScope.GLOBAL, "Global preference", ""));

        assertEquals(1, hot.revision.get());
        assertTrue(hot.cacheObservedCommittedTransaction);
    }

    private TavallMemoryWriteRequest request(TavallMemoryScope scope, String title, String supersedes) {
        return new TavallMemoryWriteRequest(
                scope,
                TavallMemoryKind.REFLECTION,
                title,
                "summary",
                List.of("fact"),
                75,
                "internal",
                "test://source",
                supersedes,
                Map.of()
        );
    }

    private TavallMemoryIdentity identity(String projectId) {
        return new TavallMemoryIdentity("user", "workspace", projectId, "chat", "session", "thread");
    }

    private TavallMemoryRuntimePolicy policy() {
        return new TavallMemoryRuntimePolicy(10, 5, 5, Duration.ofMinutes(5));
    }

    private TavallMemoryRecord record(
            String id,
            String projectId,
            TavallMemoryScope scope,
            String titleKey,
            String semanticNamespace
    ) {
        return new TavallMemoryRecord(
                id,
                "user",
                "workspace",
                "session",
                scope == TavallMemoryScope.SESSION ? "chat" : "",
                projectId,
                scope == TavallMemoryScope.SESSION ? "thread" : "",
                scope,
                TavallMemoryKind.REFLECTION,
                "title",
                titleKey,
                "summary",
                List.of("fact"),
                List.of("source"),
                1,
                "active",
                75,
                "internal",
                "explicit",
                "",
                false,
                Map.of("semanticProjectId", semanticNamespace),
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );
    }

    private static final class FakeRecordStore implements TavallMemoryRecordStore {
        private final FakeTransaction transaction = new FakeTransaction();
        private boolean committed;

        @Override
        public <T> T transact(Function<TavallMemoryTransaction, T> work) {
            T result = work.apply(transaction);
            committed = true;
            return result;
        }

        @Override
        public List<TavallMemoryRecord> loadExactState(TavallMemoryIdentity identity, int limit) {
            return transaction.lastRecord == null ? List.of() : List.of(transaction.lastRecord);
        }
    }

    private static final class FakeHotState implements TavallMemoryHotStateStore {
        private final FakeRecordStore store;
        private final AtomicInteger revision = new AtomicInteger();
        private boolean cacheObservedCommittedTransaction;

        private FakeHotState(FakeRecordStore store) {
            this.store = store;
        }

        @Override
        public long globalRevision(String authorityKey) {
            return revision.get();
        }

        @Override
        public long advanceGlobalRevision(String authorityKey) {
            return revision.incrementAndGet();
        }

        @Override
        public Optional<List<TavallMemoryRecord>> loadExactState(String cacheKey) {
            return Optional.empty();
        }

        @Override
        public void storeExactState(String cacheKey, List<TavallMemoryRecord> records, Duration ttl) {
            cacheObservedCommittedTransaction = store.committed;
        }
    }

    private static final class FakeTransaction implements TavallMemoryTransaction {
        private final Map<String, TavallMemoryRecord> byId = new HashMap<>();
        private List<TavallMemoryStableIdentity> locked = List.of();
        private int creates;
        private boolean upserted;
        private String deletedNamespace = "";
        private TavallMemoryRecord lastRecord;

        @Override
        public void lockStableIdentities(List<TavallMemoryStableIdentity> identities) {
            locked = List.copyOf(identities);
        }

        @Override
        public Optional<TavallMemoryRecord> findActive(TavallMemoryStableIdentity identity) {
            return byId.values().stream()
                    .filter(TavallMemoryRecord::active)
                    .filter(record -> TavallMemoryStableIdentity.from(record).equals(identity))
                    .findFirst();
        }

        @Override
        public TavallMemoryRecord requireById(String memoryId) {
            TavallMemoryRecord record = byId.get(memoryId);
            if (record == null) {
                throw new IllegalArgumentException("missing memory: " + memoryId);
            }
            return record;
        }

        @Override
        public TavallMemoryRecord create(
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
        ) {
            creates++;
            String id = "mem-new-" + creates;
            lastRecord = new TavallMemoryRecord(
                    id,
                    identity.userId(),
                    identity.workspaceId(),
                    identity.sessionId(),
                    stableIdentity.chatId(),
                    stableIdentity.projectId(),
                    stableIdentity.threadKey(),
                    stableIdentity.scope(),
                    stableIdentity.kind(),
                    title,
                    titleKey,
                    request.summary(),
                    request.facts(),
                    List.of(sourceEventId),
                    1,
                    "active",
                    importance,
                    sensitivity,
                    writeAuthority,
                    "",
                    false,
                    metadata,
                    OffsetDateTime.now(),
                    OffsetDateTime.now()
            );
            byId.put(id, lastRecord);
            return lastRecord;
        }

        @Override
        public TavallMemoryRecord update(
                TavallMemoryRecord existing,
                TavallMemoryWriteRequest request,
                String title,
                int importance,
                String sensitivity,
                String writeAuthority,
                List<String> sourceEventIds,
                Map<String, Object> metadata
        ) {
            lastRecord = new TavallMemoryRecord(
                    existing.memoryId(), existing.userId(), existing.workspaceId(), existing.sessionId(), existing.chatId(),
                    existing.projectId(), existing.threadKey(), existing.scope(), existing.kind(), title, existing.titleKey(),
                    request.summary(), request.facts(), sourceEventIds, existing.version() + 1, "active", importance,
                    sensitivity, writeAuthority, "", false, metadata, existing.createdAt(), OffsetDateTime.now()
            );
            byId.put(lastRecord.memoryId(), lastRecord);
            return lastRecord;
        }

        @Override
        public void supersede(String memoryId, String supersededByMemoryId) {
            TavallMemoryRecord old = requireById(memoryId);
            byId.put(memoryId, new TavallMemoryRecord(
                    old.memoryId(), old.userId(), old.workspaceId(), old.sessionId(), old.chatId(), old.projectId(), old.threadKey(),
                    old.scope(), old.kind(), old.title(), old.titleKey(), old.summary(), old.facts(), old.sourceEventIds(), old.version(),
                    "superseded", old.importance(), old.sensitivity(), old.writeAuthority(), supersededByMemoryId, false,
                    old.metadata(), old.createdAt(), OffsetDateTime.now()
            ));
        }

        @Override
        public void enqueueSemanticUpsert(TavallMemoryRecord record, String semanticNamespace, String sourceReference) {
            upserted = true;
        }

        @Override
        public void enqueueSemanticDelete(TavallMemoryRecord record, String semanticNamespace) {
            deletedNamespace = semanticNamespace;
        }
    }
}
