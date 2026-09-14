---
name: tavall-staging-reconciliation
description: Repair Tavall staging/feature PR topology while preserving useful stacks, current ownership, exact source identity, environment evidence, and unrelated parallel work.
---

# Tavall Staging Reconciliation

Use from `tavall-agent-reconciliation` or from orchestration during a bounded topology repair.

1. Inspect the complete relevant open-PR/staging graph before mutation.
2. Treat malformed metadata, duplicate active roots, ancestry cycles, stale/superseded roots, wrong-base independent work, and direct-to-main feature work that violates the active repository staging contract as findings.
3. Preserve existing feature stacks and authorship. Do not flatten descendants into staging.
4. Prefer the closest meaningful staging boundary.
5. Resolve exact participating source heads and compare them with the current Tavall environment generation/source digest.
6. When a participating head changes, retain old validation as historical evidence and resolve a matching new generation instead of relabeling old evidence.
7. Discover current repository-staging and environment capabilities from the live catalog. Use Tavall Console/CLI when that is the canonical execution path; do not regrow a bespoke MCP mutation surface.
8. Revalidate topology and exact-head evidence after repair.
9. Keep unrelated work moving. Reconciliation is local to the affected graph.

Topology repair is not source promotion, deployment, or approval of the code inside the repaired PRs.
