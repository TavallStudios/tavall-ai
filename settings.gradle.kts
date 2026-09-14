rootProject.name = "tavall-ai"

include(
    "tavall-ai-bootstrap",
    "tavall-agent-scheduler",
    "tavall-agent-orchestration",
    "tavall-agent-implementation",
    "tavall-agent-review",
    "tavall-agent-reconciliation",
    "tavall-agent-e2e",
    "tavall-agent-architecture",
    "tavall-agent-documentation",
    "tavall-agent-builder",
    "tavall-agent-recovery",
    "tavall-agent-web",
    "tavall-ai-runtime-project-context",
    "tavall-ai-runtime-model-execution",
    "tavall-ai-runtime-codex",
    "tavall-ai-runtime-distributed-execution",
    "tavall-ai-runtime-memory",
    "tavall-ai-runtime",
)

sourceControl {
    gitRepository(uri("https://github.com/TavallStudios/function-catalog.git")) {
        // Function Catalog owns callable-function/schema/view/MCP infrastructure only.
        producesModule("org.tavall:ai-core")
    }
}
