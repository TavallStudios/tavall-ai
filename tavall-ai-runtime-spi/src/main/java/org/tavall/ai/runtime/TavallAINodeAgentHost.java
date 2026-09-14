package org.tavall.ai.runtime;

import org.tavall.agent.TavallAgentRegistry;

import java.io.PrintStream;
import java.util.List;

/**
 * Transport-neutral host boundary for the Tavall AI Node Agent runtime.
 *
 * <p>The Tavall AI runtime owns process identity, agent discovery, and runtime-module validation.
 * A host adapter owns only the externally authorized job/session transport. Host metadata and
 * ServiceLoader discovery never grant Cloud, shell, credential, or target-mutation authority.</p>
 */
public interface TavallAINodeAgentHost {
    int run(TavallAgentRegistry agents, List<String> arguments, PrintStream output) throws Exception;
}
