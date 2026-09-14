# Tavall AI Plugin Staging Root

This branch is the persistent integration tree for the portable `tavall-ai-plugin` package and its client-facing agents, skills, manifests, and MCP/Function Catalog discovery contract.

```text
<!-- tavall-staging:v1 -->
Type: DOMAIN_INTEGRATION
State: ACTIVE
Branch: staging/plugin
Parent: main
Promotion: MANUAL
ChildMergeTarget: staging/plugin
```

- Plugin/package work targets `staging/plugin` unless it is inseparable from an owning runtime/domain PR.
- `plugins/tavall-ai` is the single installable Tavall AI package authority. Do not create additional installable Tavall orchestrator, Git-workflow, agent, or client-specific Tavall AI plugins.
- Client-specific manifests/adapters may live inside `plugins/tavall-ai`, but Tavall agents, orchestration rules, and capability discovery remain shared.
- Runtime/domain PRs may contribute agent or skill behavior, but plugin-facing deltas must be reconciled into this staging root before promotion so stale branches cannot restore older package identities.
- Child merges are integration only. They do not imply `main` promotion or deployment.
- When this staging root is promoted to `main`, treat that PR as completed history, recreate `staging/plugin` from the new `main`, and open the next active plugin staging PR rather than continuing to attach work to the promoted root.
