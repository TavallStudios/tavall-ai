---
name: tavall-skill-orchestrator
description: Use as the portable entry point for Tavall work. Discover the live Tavall MCP/Function Catalog and current development session, route work to bundled Tavall agent roles, and execute through Tavall Console with normal tavall, git, gh, build, and shell commands instead of inventing client-specific workflows.
---

# Tavall Skill Orchestrator

Use this skill first for Tavall-related work. It is intentionally small: discover the live Tavall surface, select the right Tavall agents, and let those agents own their acceptance units.

## Start here

1. Discover the Tavall MCP / Function Catalog surface exposed to the current AI client. Do not assume a remembered tool list is current.
2. Read Tavall status and bootstrap or resume the current DEVELOPMENT session when those capabilities are exposed.
3. Reuse the resolved environment, lane, workspace, and repository context by default. Do not create replacements merely because a new prompt or agent started.
4. Classify the work and select the smallest useful set of bundled `tavall-agent-*` roles.
5. Execute ordinary engineering operations through Tavall Console. Prefer normal `tavall`, `git`, `gh`, build, test, and shell commands over adding bespoke MCP operations.
6. Use the scheduler role only when another worker/session is justified by placement, resource pressure, process/workspace isolation, recovery, dedicated E2E infrastructure, or safely independent parallelism.
7. Validate mutation against the exact final HEAD using repository-owned validation through the authorized Tavall execution surface. Preserve recoverable Git checkpoints for meaningful work.

## Bundled Tavall agents

Use these installed roles when their acceptance unit matches the task:

- `tavall-agent-orchestration`: coordinate substantive work and select specialists.
- `tavall-agent-implementation`: bounded implementation and matching verification.
- `tavall-agent-review`: independent exact-head review and evidence assessment.
- `tavall-agent-reconciliation`: current-main, branch/PR graph, staging, ownership, and migration reconciliation.
- `tavall-agent-e2e`: realistic exact-head runtime and integration acceptance.
- `tavall-agent-architecture`: explicitly approved structural migration or systemic repair.
- `tavall-agent-documentation`: owning technical, progress, and evidence documentation.
- `tavall-agent-scheduler`: distributed worker/top-level-session placement and recovery only.

Additional bundled Tavall agent roles may be used when discovered and relevant. The live plugin contents are authoritative over this summary.

## Tavall MCP / Console contract

The portable plugin expects the current Tavall integration to expose a small discovery/execution surface rather than one MCP function per shell operation. Current compatible capabilities include:

- `cloud_status`: authoritative CONTROL readiness/topology health.
- `cloud_dev_session_bootstrap`: resolve a resumable DEVELOPMENT session from CONTROL.
- `cloud_console_execute`: bounded synchronous Tavall Console execution in a DEVELOPMENT repository.
- `cloud_dev_environment_execute`: compatibility alias for console execution; prefer `cloud_console_execute` when both exist.
- `cloud_catalog_list`: discover eligible authenticated Function Catalog capabilities.
- `cloud_catalog_describe`: inspect one capability, typed schema, placement, and authority boundary.
- `cloud_read_sandbox_artifact`: read an already-exported Tavall sandbox artifact.

Clients may expose these under different connector or namespace names. Match by capability semantics and catalog metadata instead of hard-coding a client namespace.

## Authority rules

- Tavall Cloud owns environment/lane/node placement, workspace/process/sandbox/network authority, executable and credential grants, and deployment/runtime authority.
- Function Catalog owns the canonical callable capability schemas projected through MCP.
- Tavall Console is the preferred execution path for ordinary command work.
- The repository and its canonical Tavall documentation own project-specific architecture and workflow rules.
- Agent metadata describes behavior; it does not grant authority.

## Degraded clients

If a client can load this plugin but cannot reach Tavall MCP, keep the agent/orchestration guidance available but report execution as degraded. Do not silently substitute an unrelated local workspace or fabricate remote state.

If Tavall MCP is reachable but a named compatibility function is absent, inspect the live catalog for its replacement before declaring the capability missing.
