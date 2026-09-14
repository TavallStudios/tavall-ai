# Tavall AI Plugin Orchestration

Tavall skill routing is now packaged inside the single portable `tavall-ai-plugin` at `plugins/tavall-ai`.

The package contains:

- `tavall-skill-orchestrator` as the entry point;
- its registry and health/routing reference;
- shared Git/staging/promotion policy skills;
- selective engineering/documentation policy resolution;
- memory-plane guidance;
- specialized Tavall agent skills and portable agent descriptors;
- local exact-head completion guidance;
- client manifests/adapters while keeping capability logic client-neutral.

The orchestrator exists because merely having a skill or capability somewhere does not guarantee an AI will select the correct current authority. It discovers the installed package and live Tavall MCP/Function Catalog, loads only relevant foundations, selects the smallest useful agent set, and keeps missing/degraded capabilities visible.

Standalone `tavall-skill-orchestrator` and `tavall-git-workflow` plugin packages are superseded by the bundled copies. Client prompts should install/load one Tavall AI package rather than reconstructing the routing graph themselves.

Plugin integration is owned by the active `staging/plugin` PR and `.github/TAVALL_PLUGIN_STAGING.md`.
