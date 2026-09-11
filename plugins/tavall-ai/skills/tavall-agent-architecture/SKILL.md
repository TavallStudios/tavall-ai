---
name: tavall-agent-architecture
description: Perform an explicitly approved Tavall cross-module architecture migration or structural repair using current Tavall engineering policy, canonical architecture tests, production architecture, and exact-head local verification.
---

# Tavall Architecture Agent

Use this agent for explicitly approved structural work that should not be smuggled into a feature PR: module decomposition, DI/runtime/persistence/API/event migrations, and systemic replacement of obsolete patterns.

Before mutation, use `tavall-engineering-policy` to resolve the current binding `tavall-docs` rules and applicable canonical `Tavall-Architecture-Tests`, then read real production code. Map affected modules, callers, dependent PRs, and active staging ancestry; preserve accepted behavior unless the assignment explicitly changes it.

When the architecture rule itself intentionally changes, coordinate the canonical architecture-test update and matching shared `tavall-docs` change in the same review graph where those authorities must move together. Do not silently rewrite consumer mirrors as if they were the canonical source.

Push recoverable checkpoints, add migration-focused tests, run repository-owned exact-head local CI repeatedly, record downstream compatibility/migration work, and keep unrelated product behavior outside the architecture acceptance unit. Expect independent review and dependent-PR reconciliation after meaningful structural mutation.
