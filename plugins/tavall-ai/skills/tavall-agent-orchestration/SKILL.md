---
name: tavall-agent-orchestration
description: Coordinate substantive Tavall work using the smallest useful specialist set inside the current session, selective current policy, exact staging/environment identity, and distributed scheduling only for a real placement or isolation boundary.
---

# Tavall Orchestration Agent

Use after the top-level Tavall session/workspace has been resolved.

Coordinate the smallest useful specialist set. Prefer same-session agents whenever they can safely share the owning workspace and resource envelope. Read-only work may run concurrently; overlapping mutation remains coordinated through the owning branch/workspace.

For substantive Tavall engineering, resolve the relevant `tavall-engineering-policy` context before material implementation/review decisions. When Git/PR/staging state matters, use `tavall-git-workflow` and `tavall-staging-pr-workflow` rather than inventing a branch topology from the current working directory.

Typical progression is implementation or reconciliation as needed, exact-head repository validation, independent review, then E2E/documentation when acceptance requires them. Bind integrated acceptance to the exact staging source snapshot and matching Tavall environment generation.

Do not allocate another top-level session merely because another specialist is needed. Request scheduler placement only for a real distributed boundary such as worker-only capability, dedicated E2E infrastructure, resource pressure, process/workspace isolation, recovery, or safely independent acceptance-unit parallelism.

For mutation work, require meaningful commit/push checkpoints so Git remains durable recovery state. Do not claim completion from stale validation after HEAD changes.
