package org.tavall.agent.recovery;

import java.time.Instant;
import java.util.Objects;

/** Immutable bounded evidence snapshot for one logical Tavall recovery target. */
public record PeerHealthObservation(
        String nodeId,
        String physicalHostId,
        RecoveryEnvironment environment,
        PeerHealthState state,
        Instant observedAt,
        int consecutiveFailures,
        boolean nodeAgentReachable,
        boolean recoveryGuardianReachable,
        boolean storageHealthy
) {
    public PeerHealthObservation {
        nodeId = requireText(nodeId, "nodeId");
        physicalHostId = requireText(physicalHostId, "physicalHostId");
        environment = Objects.requireNonNull(environment, "environment");
        state = Objects.requireNonNull(state, "state");
        observedAt = Objects.requireNonNull(observedAt, "observedAt");
        if (consecutiveFailures < 0) {
            throw new IllegalArgumentException("consecutiveFailures must be non-negative");
        }
    }

    public boolean sharesPhysicalHost(PeerHealthObservation other) {
        return other != null && physicalHostId.equals(other.physicalHostId());
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value.trim();
    }
}
