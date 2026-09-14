---
name: tavall-git-workflow
description: Use for Tavall branch, commit, pull-request, stacking, staging, reconciliation, review, promotion, hotfix, and GitHub workflow decisions. Read the current canonical Tavall Git workflow and stricter repository-local rules, inspect the live PR/branch graph before mutation, preserve durable PR work surfaces, and keep source promotion separate from deployment.
---

# Tavall Git Workflow

Use this bundled skill whenever repository, branch, pull-request, staging, promotion, or GitHub state may materially change.

## Authority

Resolve the current shared workflow from:

- repository: `TavallStudios/tavall-docs`
- ref: `main`
- path: `docs/quality/GIT_WORKFLOW.md`

Then read stricter repository-local authority such as `AGENTS.md`, `AGENT.MD`, contribution rules, staging manifests, synchronization contracts, release procedures, and deployment runbooks.

If the canonical document cannot be read, mark Git policy `DEGRADED`. Do not silently substitute a stale cached copy.

## Preflight graph

Before consequential Git/PR mutation inspect the relevant current branch/head, existing PR, overlapping PRs, stack ancestry, staging membership, merge/base state, validation/check state, and repository-specific constraints.

Classify work as `same_scope`, `independent`, `dependent`, `overlapping`, or `superseded` before acting. Continue an existing durable PR when it already owns the scope. Do not create replacement/v2 PRs merely because architecture changed or a branch needs reconciliation.

## Stacking and staging

Stacking is first-class. A dependent child targets its unmerged parent branch. After a parent merges, update the child from the new destination, retarget the same PR, verify only the intended child diff remains, rerun affected validation, and update staging evidence.

Staging is integration state, not a universal queue. Use the closest meaningful staging boundary. When a staging branch is used, it must remain coherent enough to validate as one future tree.

For plugin/package work, respect `.github/TAVALL_PLUGIN_STAGING.md` and the active `staging/plugin` PR. `plugins/tavall-ai` is the single installable Tavall AI package authority.

## Promotion

A staging or feature merge is not deployment. Production-source promotion to `main` and runtime deployment are separate decisions.

When an active staging root promotes, treat it as completed history and establish the next active staging root from the new `main` rather than attaching fresh work to the completed root.

## Owner authority

Authorized owner bypass, direct-main work, or history repair may be used when current policy permits it, but preserve traceability and reconcile affected open PRs/staging afterward.
