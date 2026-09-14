package org.tavall.ai.memory;

import java.time.Duration;

/** Bounded retrieval/cache policy supplied by the parent runtime. */
public record TavallMemoryRuntimePolicy(
        int exactStateLimit,
        int semanticCandidateLimit,
        int externalContextLimit,
        Duration hotStateTtl
) {
    public TavallMemoryRuntimePolicy {
        if (exactStateLimit < 1 || semanticCandidateLimit < 1 || externalContextLimit < 1) {
            throw new IllegalArgumentException("memory retrieval limits must be positive");
        }
        if (hotStateTtl == null || hotStateTtl.isZero() || hotStateTtl.isNegative()) {
            throw new IllegalArgumentException("hotStateTtl must be positive");
        }
    }

    public static TavallMemoryRuntimePolicy defaults() {
        return new TavallMemoryRuntimePolicy(24, 12, 12, Duration.ofMinutes(10));
    }
}
