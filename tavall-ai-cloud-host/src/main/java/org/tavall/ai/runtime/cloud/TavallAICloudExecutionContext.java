package org.tavall.ai.runtime.cloud;

import org.tavall.agent.TavallAgent;
import org.tavall.cloud.ai.broker.CloudAINodeAgentAssignment;

import java.nio.file.Path;
import java.util.Objects;

/** Fully validated local inputs handed from the Cloud host adapter to an execution backend. */
public record TavallAICloudExecutionContext(
        TavallAgent agent,
        CloudAINodeAgentAssignment assignment,
        Path workspace
) {
    public TavallAICloudExecutionContext {
        agent = Objects.requireNonNull(agent, "agent");
        assignment = Objects.requireNonNull(assignment, "assignment");
        workspace = Objects.requireNonNull(workspace, "workspace").toAbsolutePath().normalize();
    }
}
