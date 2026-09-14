package org.tavall.ai.memory;

import org.tavall.ai.bootstrap.TavallAIModule;
import org.tavall.ai.bootstrap.TavallAIModuleProvider;
import org.tavall.dependency.annotations.DelegatesTo;

import java.util.Set;

/** Bootstrap descriptor for the authority-scoped Tavall AI memory capability. */
@DelegatesTo
public final class MemoryModuleProvider implements TavallAIModuleProvider {
    public static final String MODULE_ID = "memory";

    @Override
    public TavallAIModule module() {
        return new TavallAIModule(
                MODULE_ID,
                "Provides explicit durable memory writes, scoped supersession, exact-state hydration, and semantic/knowledge retrieval ports.",
                Set.of()
        );
    }
}
