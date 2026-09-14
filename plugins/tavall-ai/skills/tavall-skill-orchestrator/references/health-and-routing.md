# Tavall Skill Health and Routing

## Per-request health gate

1. Build a candidate route from prompt intent plus current repository/runtime context.
2. Add only relevant foundations: Git workflow when repository/PR state may change, engineering policy for Tavall engineering, documentation routing for governed concerns, memory when available, staging workflow when PR integration matters, and exact-head completion when a diff exists.
3. Discover installed plugin identities and live MCP/Function Catalog capabilities.
4. Detect duplicate authority/identity instead of silently selecting by load order.
5. Resolve required dependencies.
6. Classify each needed surface as `AVAILABLE`, `DEGRADED`, `MISSING`, `SKIPPED_BY_SCOPE`, or `BLOCKED`.
7. Execute only after required policy/authority dependencies have a safe state.
8. Re-run the gate when the task crosses into a new domain.

## Default substantive Tavall route

```text
tavall-skill-orchestrator
-> current Tavall session + live capability discovery
-> tavall-memory-plane when available
-> tavall-git-workflow when repository/PR state is involved
-> tavall-engineering-policy for engineering concerns
-> tavall-agent-orchestration
-> smallest useful specialist set
-> repository-owned exact-head validation for diffs
-> review/E2E/documentation as required
-> verified memory writeback only when reusable
```

Use `tavall-agent-scheduler` only for a real placement/isolation/resource/recovery boundary. Use `tavall-ai-distributed-execution` only for bounded model execution over already-authorized providers/runtimes.

## Staging route

```text
tavall-git-workflow
-> tavall-staging-pr-workflow
-> exact PR/staging source snapshot
-> matching Tavall lane/environment generation
-> implementation/review/E2E
-> tavall-staging-reconciliation when topology changes
-> tavall-staging-promotion only at the promotion boundary
```

Old environment validation never transfers to a changed source snapshot merely because the branch name stayed the same.

## Degraded behavior

- Memory missing: continue from current source/runtime evidence when safe and report memory degraded.
- Documentation routing index missing: targeted current-tree lookup only; never recursive preload.
- Named MCP function missing: discover the live replacement by semantics/catalog before declaring the capability absent.
- Tavall execution unavailable: do not substitute an unrelated local workspace or fabricate remote state.
- Exact-head validation unavailable: do not claim a diff-producing task is fully accepted.

## Domain examples

- Web: `tavall-agent-web` + real product/browser evidence + implementation/review/E2E as needed.
- Builder: `tavall-agent-builder` + authoritative Project Novus Builder/Studio evidence.
- Recovery: `tavall-agent-recovery` + bounded current health + lowest-risk authorized mutation + verification.
- Reconciliation: `tavall-agent-reconciliation` + live Git/PR graph + staging/environment evidence.

The registry is a bootstrap map, not a frozen catalog. Live plugin contents and current Function Catalog metadata win over stale aliases.
