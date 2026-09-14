package org.tavall.ai.memory;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Single provider-neutral exact + semantic + structural/temporal memory hydration path. */
public final class TavallMemoryRetrievalService {
    private final TavallMemoryRecordStore recordStore;
    private final TavallMemoryHotStateStore hotStateStore;
    private final TavallMemorySemanticStore semanticStore;
    private final List<TavallMemoryKnowledgeProvider> knowledgeProviders;
    private final TavallMemoryRuntimePolicy policy;

    public TavallMemoryRetrievalService(
            TavallMemoryRecordStore recordStore,
            TavallMemoryHotStateStore hotStateStore,
            TavallMemorySemanticStore semanticStore,
            List<TavallMemoryKnowledgeProvider> knowledgeProviders,
            TavallMemoryRuntimePolicy policy
    ) {
        this.recordStore = Objects.requireNonNull(recordStore, "recordStore");
        this.hotStateStore = Objects.requireNonNull(hotStateStore, "hotStateStore");
        this.semanticStore = Objects.requireNonNull(semanticStore, "semanticStore");
        this.knowledgeProviders = knowledgeProviders == null ? List.of() : List.copyOf(knowledgeProviders);
        this.policy = Objects.requireNonNull(policy, "policy");
    }

    public TavallMemoryHydration hydrate(
            TavallMemoryIdentity identity,
            String repoPath,
            String query,
            Map<String, Object> metadata
    ) {
        Objects.requireNonNull(identity, "identity");
        String normalizedQuery = TavallMemoryIdentity.clean(query);
        Map<String, Object> requestMetadata = metadata == null ? Map.of() : Map.copyOf(metadata);
        List<TavallMemoryRecord> exact = loadExactState(identity);
        List<TavallMemoryKnowledgeContext> knowledge = new ArrayList<>();
        List<TavallMemorySemanticMatch> semantic = List.of();

        if (!normalizedQuery.isBlank() && !identity.projectId().isBlank()) {
            try {
                Map<String, Object> required = new LinkedHashMap<>();
                required.put("userId", identity.userId());
                required.put("workspaceId", identity.workspaceId());
                required.put("status", "active");
                required.put("tombstoned", false);
                semantic = semanticStore.search(
                        identity,
                        normalizedQuery,
                        policy.semanticCandidateLimit(),
                        Map.copyOf(required)
                ).stream()
                        .sorted(Comparator.comparingDouble(this::compositeScore).reversed())
                        .limit(policy.semanticCandidateLimit())
                        .toList();
            } catch (RuntimeException exception) {
                knowledge.add(degraded("semantic", "SEMANTIC", exception));
            }
        }

        for (TavallMemoryKnowledgeProvider provider : knowledgeProviders) {
            try {
                TavallMemoryKnowledgeContext context = provider.retrieve(
                        identity,
                        TavallMemoryIdentity.clean(repoPath),
                        normalizedQuery,
                        policy.externalContextLimit(),
                        requestMetadata
                );
                if (context != null) {
                    knowledge.add(context);
                }
            } catch (RuntimeException exception) {
                knowledge.add(degraded(provider.id(), "EXTERNAL", exception));
            }
        }
        return new TavallMemoryHydration(exact, semantic, knowledge);
    }

    public List<TavallMemoryRecord> loadExactState(TavallMemoryIdentity identity) {
        long revision = hotStateStore.globalRevision(identity.authorityKey());
        String cacheKey = identity.exactStateKey(revision);
        return hotStateStore.loadExactState(cacheKey).orElseGet(() -> {
            List<TavallMemoryRecord> records = recordStore.loadExactState(identity, policy.exactStateLimit());
            hotStateStore.storeExactState(cacheKey, records, policy.hotStateTtl());
            return records;
        });
    }

    private double compositeScore(TavallMemorySemanticMatch match) {
        Map<String, Object> metadata = match.metadata();
        double recency = recencyBoost(string(metadata, "updatedAt"));
        double importance = number(metadata, "importance") / 100.0D;
        double scope = switch (string(metadata, "scope")) {
            case "SESSION" -> 1.0D;
            case "PROJECT" -> 0.6D;
            case "GLOBAL" -> 0.3D;
            default -> 0.1D;
        };
        return (match.score() * 0.65D) + (recency * 0.15D) + (importance * 0.15D) + (scope * 0.05D);
    }

    private double recencyBoost(String updatedAt) {
        if (updatedAt.isBlank()) {
            return 0.1D;
        }
        try {
            long days = Math.max(0L, ChronoUnit.DAYS.between(OffsetDateTime.parse(updatedAt), OffsetDateTime.now()));
            return 1.0D / (1.0D + days);
        } catch (RuntimeException ignored) {
            return 0.1D;
        }
    }

    private TavallMemoryKnowledgeContext degraded(String providerId, String role, RuntimeException exception) {
        String message = exception.getMessage() == null ? exception.getClass().getSimpleName() : exception.getMessage();
        return new TavallMemoryKnowledgeContext(providerId, role, "", List.of(), Map.of(), true, message);
    }

    private String string(Map<String, Object> metadata, String key) {
        Object value = metadata.get(key);
        return value == null ? "" : String.valueOf(value).strip();
    }

    private double number(Map<String, Object> metadata, String key) {
        Object value = metadata.get(key);
        return value instanceof Number number ? number.doubleValue() : 0.0D;
    }
}
