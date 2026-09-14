---
name: tavall-staging-pr-workflow
description: Resolve and preserve Tavall staging PR ancestry and bind the selected exact source snapshot to the matching Tavall lane/environment evidence before implementation, integration, review, or acceptance.
---

# Tavall Staging PR Workflow

Use this skill whenever Tavall work creates, changes, reviews, validates, or coordinates pull requests or staging roots.

The Git/PR graph owns source/integration identity. Tavall Cloud owns execution/environment identity. Neither replaces the other.

## Workflow

1. Inspect the live PR graph and active `tavall-staging:v1` roots before choosing a base.
2. Classify the work as independent, dependent, overlapping, same-scope, or superseded.
3. Preserve existing feature stacks. Do not flatten descendants merely because a staging root exists.
4. Select the closest meaningful active staging boundary for independent work.
5. Resolve every participating repository to an exact commit SHA and form one source-snapshot identity.
6. Discover the current Tavall Cloud/Function Catalog capabilities, then resolve or inspect the lane and immutable environment generation matching that exact source snapshot. Prefer catalog discovery and Tavall Console/CLI over hard-coded client-specific tool names.
7. Inspect environment components and validation evidence. Never reuse a PASS from another source-snapshot digest.
8. Implement/review/validate on the resolved exact heads and preserve meaningful Git checkpoints.
9. Re-inspect topology and environment evidence whenever a participating head changes.

## Semantics

- Feature PR = focused change/review truth.
- Staging PR = combined future-tree/integration truth.
- Lane/environment generation = execution and evidence truth for one immutable source snapshot.
- Child-to-staging merge means integrated for combined validation, not production-ready.
- Staging-to-main promotion is separate from deployment.

If a remembered staging or Cloud capability is absent, discover the current replacement before declaring the workflow unavailable.
