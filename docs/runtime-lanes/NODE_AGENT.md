# Tavall AI NODE_AGENT Runtime Lane

<!-- tavall-staging:v1 -->
Type: DOMAIN_INTEGRATION
State: ACTIVE
Branch: staging/runtime-node-agent
Parent: `staging/runtime` / PR #7
Promotion: MANUAL
ChildMergeTarget: staging/runtime-node-agent

## Purpose

Durable integration and validation lane for the Tavall AI `NODE_AGENT` executable composition.

Shared runtime integration belongs to `staging/runtime`. Portable Tavall AI plugin packaging, agents, orchestration skills, and client-facing plugin adapters belong to the active `staging/plugin` root. Reusable `tavall-agent-*` runtime packages and shared runtime capability modules remain with their owning runtime/domain PRs.

This lane owns Node-Agent-host-specific runtime composition, authorized host adapters, lifecycle/readiness, deployment wiring, and exact DEVELOPMENT acceptance. It must not recreate a Node-Agent-specific Tavall plugin or a second capability catalog.

## Acceptance focus

- Java 25 repository-local verification and staged distribution checks when runtime code changes;
- Node Agent runtime startup/shutdown and installed-agent discovery;
- runtime-module requirement validation;
- bounded model/distributed execution through authorized host adapters;
- Tavall Cloud/CONTROL job, lane/environment, workspace, process, executable, and credential authority remaining external and fail-closed;
- restart/recovery, rollback, exact source/environment binding, and untested-path evidence.

A child merge into this lane is integration evidence only. It does not imply `main` promotion or deployment.
