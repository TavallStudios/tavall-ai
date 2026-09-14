# Tavall AI CHATGPT_WEB Runtime Lane

<!-- tavall-staging:v1 -->
Type: DOMAIN_INTEGRATION
State: ACTIVE
Branch: staging/runtime-chatgpt-web
Parent: `staging/runtime` / PR #7
Promotion: MANUAL
ChildMergeTarget: staging/runtime-chatgpt-web

## Purpose

Durable integration and validation lane for the Tavall AI `CHATGPT_WEB` executable composition.

Shared runtime integration belongs to `staging/runtime`. Portable client-facing Tavall AI plugin packaging, agents, orchestration skills, and plugin adapters belong to the active `staging/plugin` root. Reusable `tavall-agent-*` runtime packages and shared runtime capability modules remain with their owning runtime/domain PRs.

This lane owns ChatGPT-Web-host-specific runtime composition, lifecycle/readiness, deployment wiring, transport integration, and exact DEVELOPMENT acceptance. It must not recreate a ChatGPT-specific Tavall agent/orchestration package or a second capability authority.

## Acceptance focus

- Java 25 repository-local verification and staged distribution checks when runtime code changes;
- ChatGPT Web runtime startup/shutdown and installed-agent discovery;
- runtime-module requirement validation;
- Tavall plugin/MCP/Function Catalog transport compatibility through the authorized adapter boundary;
- Tavall Console/Cloud operational authority remaining external and fail-closed;
- restart/recovery, rollback, exact source/environment binding, and untested-path evidence.

A child merge into this lane is integration evidence only. It does not imply `main` promotion or deployment.
