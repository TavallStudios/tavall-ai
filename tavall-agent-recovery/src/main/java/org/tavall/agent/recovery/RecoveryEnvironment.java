package org.tavall.agent.recovery;

/** Trust environment of the observed logical target. This value never grants authority by itself. */
public enum RecoveryEnvironment {
    DEVELOPMENT,
    STAGING,
    PRODUCTION
}
