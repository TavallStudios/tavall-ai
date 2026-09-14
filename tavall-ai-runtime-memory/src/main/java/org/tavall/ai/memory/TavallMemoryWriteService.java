package org.tavall.ai.memory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/** Canonical explicit durable-memory write and supersession boundary. */
public final class TavallMemoryWriteService {
    public static final String EXPLICIT_WRITE_AUTHORITY = "explicit";
    private static final String SEMANTIC_NAMESPACE = "semanticProjectId";

    private final TavallMemoryRecordStore recordStore;
    private final TavallMemoryHotStateStore hotStateStore;
    private final TavallMemoryRuntimePolicy policy;

    public TavallMemoryWriteService(
            TavallMemoryRecordStore recordStore,
            TavallMemoryHotStateStore hotStateStore,
            TavallMemoryRuntimePolicy policy
    ) {
        this.recordStore = Objects.requireNonNull(recordStore, "recordStore");
        this.hotStateStore = Objects.requireNonNull(hotStateStore, "hotStateStore");
        this.policy = Objects.requireNonNull(policy, "policy");
    }

    public TavallMemoryRecord record(TavallMemoryIdentity identity, TavallMemoryWriteRequest request) {
        Objects.requireNonNull(identity, "identity");
        Objects.requireNonNull(request, "request");

        TavallMemoryScope scope = request.scope() == null ? TavallMemoryScope.PROJECT : request.scope();
        TavallMemoryKind kind = request.kind() == null ? TavallMemoryKind.REFLECTION : request.kind();
        requireScopeIdentity(identity, scope);
        String title = requireText(request.title(), "title");
        String summary = requireText(request.summary(), "summary");
        String titleKey = normalizeKey(title);
        int importance = request.importance() == null ? 75 : Math.max(0, Math.min(100, request.importance()));
        String sensitivity = clean(request.sensitivity()).isBlank() ? "internal" : clean(request.sensitivity());
        String sourceReference = clean(request.sourceReference());
        String sourceEventId = sourceReference.isBlank() ? "explicit-memory:" + titleKey : sourceReference;
        String supersedesMemoryId = clean(request.supersedesMemoryId());
        Map<String, Object> metadata = explicitMetadata(identity, request.metadata(), sourceReference);
        TavallMemoryStableIdentity stableIdentity = TavallMemoryStableIdentity.from(identity, scope, kind, titleKey);

        TavallMemoryRecord committed = recordStore.transact(transaction -> {
            if (supersedesMemoryId.isBlank()) {
                transaction.lockStableIdentities(List.of(stableIdentity));
                TavallMemoryRecord record = transaction.findActive(stableIdentity)
                        .map(existing -> transaction.update(
                                existing,
                                request,
                                title,
                                importance,
                                sensitivity,
                                EXPLICIT_WRITE_AUTHORITY,
                                mergeSources(existing.sourceEventIds(), sourceEventId),
                                metadata
                        ))
                        .orElseGet(() -> transaction.create(
                                identity,
                                stableIdentity,
                                request,
                                title,
                                titleKey,
                                importance,
                                sensitivity,
                                EXPLICIT_WRITE_AUTHORITY,
                                sourceEventId,
                                metadata
                        ));
                enqueueUpsert(transaction, record, sourceReference);
                return record;
            }

            TavallMemoryRecord candidate = transaction.requireById(supersedesMemoryId);
            authorizeSupersession(identity, scope, candidate);
            TavallMemoryStableIdentity targetIdentity = TavallMemoryStableIdentity.from(candidate);
            transaction.lockStableIdentities(List.of(stableIdentity, targetIdentity).stream().distinct().sorted().toList());

            TavallMemoryRecord superseded = transaction.requireById(supersedesMemoryId);
            authorizeSupersession(identity, scope, superseded);
            transaction.findActive(stableIdentity).ifPresent(conflict -> {
                if (!conflict.memoryId().equals(superseded.memoryId())) {
                    throw new IllegalArgumentException(
                            "an active memory already owns the replacement stable identity: " + conflict.memoryId()
                    );
                }
            });

            TavallMemoryRecord replacement = transaction.create(
                    identity,
                    stableIdentity,
                    request,
                    title,
                    titleKey,
                    importance,
                    sensitivity,
                    EXPLICIT_WRITE_AUTHORITY,
                    sourceEventId,
                    metadata
            );
            transaction.supersede(superseded.memoryId(), replacement.memoryId());
            String oldNamespace = semanticNamespace(superseded);
            if (!oldNamespace.isBlank()) {
                transaction.enqueueSemanticDelete(superseded, oldNamespace);
            }
            enqueueUpsert(transaction, replacement, sourceReference);
            return replacement;
        });

        refreshExactStateAfterCommit(identity, scope);
        return committed;
    }

    private void enqueueUpsert(TavallMemoryTransaction transaction, TavallMemoryRecord record, String sourceReference) {
        String namespace = semanticNamespace(record);
        if (!namespace.isBlank()) {
            transaction.enqueueSemanticUpsert(record, namespace, sourceReference);
        }
    }

    private void authorizeSupersession(
            TavallMemoryIdentity identity,
            TavallMemoryScope replacementScope,
            TavallMemoryRecord record
    ) {
        if (!record.active()) {
            throw new IllegalArgumentException("superseded memory must be active: " + record.memoryId());
        }
        if (!record.userId().equals(identity.userId()) || !record.workspaceId().equals(identity.workspaceId())) {
            throw inaccessible(record.memoryId());
        }
        if (record.scope() != replacementScope) {
            throw new IllegalArgumentException(
                    "superseding memory must preserve scope: existing=" + record.scope() + ", replacement=" + replacementScope
            );
        }
        boolean accessible = switch (record.scope()) {
            case GLOBAL -> true;
            case PROJECT -> clean(record.projectId()).equals(identity.projectId());
            case SESSION -> clean(record.projectId()).equals(identity.projectId())
                    && clean(record.chatId()).equals(identity.chatId())
                    && clean(record.threadKey()).equals(identity.threadKey());
        };
        if (!accessible) {
            throw inaccessible(record.memoryId());
        }
    }

    private void requireScopeIdentity(TavallMemoryIdentity identity, TavallMemoryScope scope) {
        if (scope != TavallMemoryScope.GLOBAL && identity.projectId().isBlank()) {
            throw new IllegalArgumentException(scope + " memory requires projectId");
        }
        if (scope == TavallMemoryScope.SESSION && (identity.chatId().isBlank() || identity.threadKey().isBlank())) {
            throw new IllegalArgumentException("SESSION memory requires chatId and threadKey");
        }
    }

    private Map<String, Object> explicitMetadata(
            TavallMemoryIdentity identity,
            Map<String, Object> requested,
            String sourceReference
    ) {
        Map<String, Object> metadata = new LinkedHashMap<>(requested == null ? Map.of() : requested);
        metadata.put("writeMode", EXPLICIT_WRITE_AUTHORITY);
        if (!identity.projectId().isBlank()) {
            metadata.put(SEMANTIC_NAMESPACE, identity.projectId());
        }
        if (!sourceReference.isBlank()) {
            metadata.put("sourceReference", sourceReference);
        }
        return Map.copyOf(metadata);
    }

    private List<String> mergeSources(List<String> existing, String sourceEventId) {
        List<String> sources = new ArrayList<>(existing == null ? List.of() : existing);
        sources.add(sourceEventId);
        return List.copyOf(new LinkedHashSet<>(sources));
    }

    private void refreshExactStateAfterCommit(TavallMemoryIdentity identity, TavallMemoryScope scope) {
        try {
            if (scope == TavallMemoryScope.GLOBAL) {
                hotStateStore.advanceGlobalRevision(identity.authorityKey());
            }
            long revision = hotStateStore.globalRevision(identity.authorityKey());
            List<TavallMemoryRecord> records = recordStore.loadExactState(identity, policy.exactStateLimit());
            hotStateStore.storeExactState(identity.exactStateKey(revision), records, policy.hotStateTtl());
        } catch (RuntimeException ignored) {
            // Canonical commit already succeeded. Cache repair is deliberately best-effort after commit.
        }
    }

    private String semanticNamespace(TavallMemoryRecord record) {
        Object value = record.metadata().get(SEMANTIC_NAMESPACE);
        String tracked = value == null ? "" : String.valueOf(value).strip();
        return tracked.isBlank() ? clean(record.projectId()) : tracked;
    }

    private IllegalArgumentException inaccessible(String memoryId) {
        return new IllegalArgumentException("memory is outside the current authority scope: " + memoryId);
    }

    private String normalizeKey(String value) {
        return value.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-+|-+$", "");
    }

    private String requireText(String value, String name) {
        String normalized = clean(value);
        if (normalized.isBlank()) {
            throw new IllegalArgumentException(name + " is required for explicit memory writes");
        }
        return normalized;
    }

    private String clean(String value) {
        return TavallMemoryIdentity.clean(value);
    }
}
