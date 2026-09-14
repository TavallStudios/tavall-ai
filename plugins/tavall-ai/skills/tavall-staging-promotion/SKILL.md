---
name: tavall-staging-promotion
description: Prepare a Tavall staging root for separately authorized promotion by freezing scope and collecting exact-head topology, environment, validation, review, risk, and rollback evidence.
---

# Tavall Staging Promotion

Use only at a staging-root promotion boundary.

1. Freeze the intended staging scope when the current repository workflow supports explicit staging state.
2. Validate the PR/staging graph and repair topology defects before acceptance.
3. Run repository-owned verification against the exact staging head.
4. Resolve/inspect the Tavall lane/environment generation matching the exact participating source snapshot and verify required PASS evidence belongs to that digest.
5. Collect accountable review, E2E/runtime evidence when applicable, migration/compatibility findings, risk, rollback, and post-promotion reconciliation state.
6. Discover and use current staging-promotion/catalog capabilities when available; otherwise perform the equivalent GitHub/Tavall workflow explicitly through the authorized surfaces.
7. Promote to `main` only through the separately authorized source-control action. Deployment remains separate.

After promotion, the completed staging PR is historical evidence. Recreate the next active staging root from the new `main`; do not attach new work to the completed root.
