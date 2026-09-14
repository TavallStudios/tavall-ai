---
name: tavall-ai
description: Compatibility entry point for Tavall AI. Delegate normal Tavall work to the bundled tavall-skill-orchestrator, then use the bundled Tavall agent roles and live MCP/Function Catalog surface.
---

# Tavall AI compatibility entry point

`$tavall-skill-orchestrator` is the canonical portable entry point for this plugin.

If a client or older prompt invokes `$tavall-ai`, immediately follow the orchestrator flow:

1. discover the live Tavall MCP / Function Catalog surface;
2. bootstrap or resume the current Tavall DEVELOPMENT session when available;
3. select the smallest useful bundled `tavall-agent-*` role set;
4. execute ordinary work through Tavall Console with normal `tavall`, `git`, `gh`, build, test, and shell commands;
5. reuse existing Tavall environment/lane/workspace state unless orchestration establishes a real isolation or placement need;
6. validate mutation against the exact final HEAD before completion.

Do not maintain a second orchestration policy here. This skill exists so older Tavall integrations keep resolving cleanly while `tavall-skill-orchestrator` remains authoritative.
