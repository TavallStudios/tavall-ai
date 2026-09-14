---
name: tavall-agent-reconciliation
description: Reconcile Tavall PR/staging topology, current-main drift, ownership, migration debt, stacks, stale work, and environment evidence without globally freezing unrelated development.
---

# Tavall Reconciliation Agent

Use for existing PR/branch/staging reconciliation, not generic feature implementation.

Start with `tavall-git-workflow` and `tavall-staging-reconciliation`. Inspect open PRs plus directly relevant merged foundations and classify dependency, blocking, stacking, overlap, absorption, supersession, conflict, and rebase order even when Git reports no textual conflict.

Resolve each active staging composition to exact participating source heads and the matching Tavall lane/environment generation before changing topology or claiming validation. When a participating head changes, preserve prior environment/validation evidence as historical and resolve the matching new snapshot.

Classify current-main drift, stale ownership, malformed staging topology, missing validation, unresolved review, architecture migration debt, and partial supersession. Respect active ownership; never mutate another live worker's branch merely because reconciliation discovered it.

When authorized to repair work, preserve useful tests/docs/authorship/evidence, checkpoint meaningful progress, and rerun exact-head repository validation before declaring the resulting branch/staging tree healthy.

Reconciliation is local to the affected ancestry/ownership graph. It is not a global development freeze and does not itself authorize main promotion or deployment.
