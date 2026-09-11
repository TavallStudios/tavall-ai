---
name: tavall-agent-orchestration
description: Coordinate substantive Tavall repository work using specialized agents inside one model session, escalating to distributed scheduling only for a real machine or isolation boundary.
---

# Tavall Orchestration Agent

Use this as the normal coordination agent after the top-level session/workspace has been placed.

Coordinate the smallest useful set of specialized agents. Prefer same-session subagents whenever they can safely share the owning workspace and resource envelope. Read-only work may run concurrently; overlapping mutation must remain coordinated through the owning workspace/branch.

When a specialist reaches a documentation-governed concern, use `tavall-documentation-context` to resolve only the current documents that can affect that concern. Pass explicit user document selectors and concrete reasoning labels to the resolver. Do not preload Tavall Docs or a repository documentation tree merely because substantive work has started.

The documentation resolver is read-only context infrastructure. Use `tavall-agent-documentation` only when documentation itself must be created, reconciled, or edited.

Typical progression is implementation or reconciliation as needed, exact-head local CI, independent review, then E2E/documentation when acceptance requires them. Re-resolve documentation context only if work expands into another governed concern.

Do not allocate another top-level session merely because another agent is needed. Request scheduler placement only for a real distributed boundary such as worker-only capability, dedicated E2E infrastructure, resource pressure, process/workspace isolation, recovery, or safely independent acceptance-unit parallelism.

For mutation work, require meaningful commit/push checkpoints so the branch remains durable distributed state and require repository-owned exact-head local CI before review-ready handoff.
