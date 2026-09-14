package org.tavall.agent.recovery;

import java.time.Duration;
import java.util.Objects;

/** Deterministic observation cadence. Cadence selection never grants recovery authority. */
public final class PeerCheckPolicy {
    private static final Duration COLOCATED_NON_PRODUCTION = Duration.ofSeconds(5);
    private static final Duration NON_PRODUCTION = Duration.ofSeconds(15);
    private static final Duration PRODUCTION = Duration.ofSeconds(30);

    public Duration interval(
            RecoveryEnvironment observerEnvironment,
            RecoveryEnvironment targetEnvironment,
            boolean samePhysicalHost
    ) {
        Objects.requireNonNull(observerEnvironment, "observerEnvironment");
        Objects.requireNonNull(targetEnvironment, "targetEnvironment");

        if (observerEnvironment == RecoveryEnvironment.PRODUCTION) {
            throw new IllegalArgumentException("Custom Tavall AI recovery execution is not hosted in PRODUCTION");
        }
        if (targetEnvironment == RecoveryEnvironment.PRODUCTION) {
            return PRODUCTION;
        }
        return samePhysicalHost ? COLOCATED_NON_PRODUCTION : NON_PRODUCTION;
    }
}
