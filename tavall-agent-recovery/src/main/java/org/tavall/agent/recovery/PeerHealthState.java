package org.tavall.agent.recovery;

/** Health state derived from bounded current Tavall evidence. */
public enum PeerHealthState {
    HEALTHY,
    DEGRADED,
    CRITICAL,
    UNREACHABLE,
    RECOVERING
}
