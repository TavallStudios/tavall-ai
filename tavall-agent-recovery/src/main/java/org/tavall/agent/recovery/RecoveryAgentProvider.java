package org.tavall.agent.recovery;

import org.tavall.agent.TavallAgent;
import org.tavall.agent.TavallAgentCapability;
import org.tavall.agent.TavallAgentInstructions;
import org.tavall.agent.TavallAgentKind;
import org.tavall.agent.TavallAgentProvider;
import org.tavall.dependency.annotations.DelegatesTo;

import java.util.Set;

/** Bounded recovery-planning agent; concrete infrastructure mutation remains host-authorized. */
@DelegatesTo
public final class RecoveryAgentProvider implements TavallAgentProvider {
    public static final String AGENT_ID = "recovery";

    @Override
    public TavallAgent agent() {
        return new TavallAgent(
                AGENT_ID,
                "Inspects bounded health evidence and produces typed low-risk recovery proposals without acquiring infrastructure authority.",
                TavallAgentKind.CONTROL,
                TavallAgentInstructions.load(RecoveryAgentProvider.class, "ROLE.md"),
                Set.of(),
                Set.of(),
                Set.of(
                        TavallAgentCapability.FUNCTION_DISCOVERY,
                        TavallAgentCapability.PEER_SUPERVISION,
                        TavallAgentCapability.SYSTEM_HEALTH_READ,
                        TavallAgentCapability.RECOVERY_PLANNING,
                        TavallAgentCapability.RECOVERY_ACTION_REQUEST
                ),
                false,
                false
        );
    }
}
