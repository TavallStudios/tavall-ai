---
name: tavall-skill-orchestrator
description: Portable entry point for Tavall work. Discover the installed Tavall AI plugin, live MCP/Function Catalog and DEVELOPMENT session, load only relevant policy/foundation skills, route work to bundled Tavall agents, and execute through Tavall Console instead of inventing client-specific workflows.
---

# Tavall Skill Orchestrator

Use this first for Tavall work. The plugin is the capability package; ChatGPT, Gemini/Antigravity, Codex, and other clients are adapters.

`registry.yaml` is the bootstrap routing map and `references/health-and-routing.md` defines degraded/health behavior. Live plugin contents and live Function Catalog metadata override stale aliases.

## Start here

1. Discover the Tavall MCP/Function Catalog surface exposed to the current client. Match capabilities by semantics and catalog metadata rather than assuming a client namespace.
2. Read Tavall status and bootstrap/resume the current DEVELOPMENT session when those capabilities exist.
3. Reuse resolved lane, environment, workspace, repository, and session context by default. Do not create replacements just because another prompt/agent started.
4. Select only the foundations relevant to the task:
   - `tavall-git-workflow` for Git/PR/staging/promotion decisions;
   - `tavall-engineering-policy` for Tavall engineering concerns;
   - `tavall-documentation-context` for governed documentation lookup;
   - `tavall-memory-plane` when memory is available and useful;
   - `tavall-staging-pr-workflow` when integration/staging state matters.
5. Classify the acceptance units and select the smallest useful set of bundled `tavall-agent-*` roles.
6. Execute ordinary work through Tavall Console. Prefer normal `tavall`, `git`, `gh`, build, test, and shell commands over adding bespoke MCP operations.
7. Use the scheduler only when another worker/session is justified by placement, resource pressure, process/workspace isolation, recovery, dedicated E2E infrastructure, or safely independent parallelism.
8. Validate mutations against the exact final HEAD/source snapshot using repository-owned validation through the authorized Tavall surface. Preserve meaningful Git checkpoints.

## Bundled Tavall agents

- `tavall-agent-orchestration`: coordinate substantive work and specialists.
- `tavall-agent-implementation`: bounded implementation and matching verification.
- `tavall-agent-review`: independent exact-head review.
- `tavall-agent-reconciliation`: current-main, branch/PR graph, staging, ownership, and migration reconciliation.
- `tavall-agent-e2e`: realistic runtime/integration acceptance.
- `tavall-agent-architecture`: explicitly approved structural migration/systemic repair.
- `tavall-agent-documentation`: owning technical/progress/evidence documentation.
- `tavall-agent-scheduler`: distributed worker/top-level-session placement.
- `tavall-agent-recovery`: bounded infrastructure/service recovery coordination.
- `tavall-agent-builder`: Project Novus Builder/Builder Studio coordination.
- `tavall-agent-web`: web/UI product design, product-intelligence, and browser acceptance.

Additional bundled roles may be used when discovered. Plugin contents are authoritative over this summary.

## Staging and environment evidence

Git/PR staging and Tavall environments are complementary:

- PR/staging graph selects exact source/integration identity.
- Tavall lane/environment generation selects execution/evidence identity for that exact source snapshot.
- If a participating head changes, old environment validation remains historical; resolve/validate the matching new snapshot.
- Promotion to `main` is separate from deployment.
- When a staging root promotes, establish the next staging root from the new `main` rather than attaching fresh work to completed history.

## Tavall MCP / Console compatibility surface

Current compatible bootstrap semantics include:

- status: `cloud_status`
- DEVELOPMENT session bootstrap: `cloud_dev_session_bootstrap`
- console execution: `cloud_console_execute` (or compatibility alias `cloud_dev_environment_execute`)
- catalog discovery: `cloud_catalog_list` / `cloud_catalog_describe`
- artifact read: `cloud_read_sandbox_artifact`

Domain capabilities may be discovered beyond this small surface. If a remembered function is absent, inspect the live catalog for the replacement before declaring the capability missing.

## Authority

- Tavall Cloud/CONTROL owns lane/environment/node placement, workspaces, processes, network, executable/credential grants, and deployment/runtime authority.
- Function Catalog owns canonical callable schemas and capability metadata.
- Tavall Console is the preferred ordinary execution path.
- Repositories and current Tavall documentation own project architecture/workflow rules.
- Agent/plugin metadata describes behavior; it never grants authority.

## Degraded clients

If the plugin loads but Tavall MCP is unavailable, retain routing/agent guidance but report remote execution degraded. Do not silently substitute an unrelated workspace or fabricate state.
