# Installation and Acceptance

## Installation model

Install **one** Tavall AI package: `plugins/tavall-ai` (`tavall-ai-plugin`).

That package now carries the Tavall orchestrator, Git workflow, staging workflow, selective engineering/documentation policy, memory routing, specialized agents, local-CI completion guidance, and client manifests/adapters. Do not install standalone Tavall orchestrator or Git-workflow plugins alongside it.

Use `tavall-skill-orchestrator` as the normal Tavall entry point. The prompt should only need to ask the client to load the installed Tavall plugin and orchestrator; routing policy belongs in the package rather than being recopied into every prompt.

## Capability model

The package discovers the current Tavall MCP/Function Catalog and DEVELOPMENT session at runtime. It prefers Tavall Console and normal `tavall`, `git`, `gh`, build, test, and shell commands for ordinary execution. Client-specific transport is an adapter concern.

Memory, browser/design, Builder, recovery, repository-staging, and other domain capabilities may degrade when their authoritative runtime surface is unavailable. Missing capability must remain visible; do not fabricate a fallback authority.

## Acceptance checks

1. Marketplace exposes exactly one Tavall AI package: `tavall-ai-plugin` -> `./plugins/tavall-ai`.
2. `tavall-skill-orchestrator` resolves from inside that package.
3. `tavall-git-workflow`, staging workflow, selective policy/docs, memory-plane guidance, and exact-head completion guidance resolve from the same package.
4. Core and domain `tavall-agent-*` roles are discoverable from the package, including orchestration, implementation, review, reconciliation, E2E, architecture, documentation, scheduler, Builder, Web, and Recovery.
5. Agent selection does not create another top-level worker unless placement/isolation/resources/recovery actually require it.
6. The live Tavall catalog is discovered before assuming domain capabilities.
7. Diff-producing work binds validation to the exact final HEAD/source snapshot.
8. PR/staging acceptance binds the exact source snapshot to the matching Tavall lane/environment generation.
9. A missing required foundation/capability produces visible `DEGRADED`, `MISSING`, or `BLOCKED` state rather than silent omission.
10. Plugin JSON/YAML/frontmatter parse cleanly and no duplicate installable Tavall AI plugin identity remains.

## Staging

Plugin changes integrate through the active `staging/plugin` PR described by `.github/TAVALL_PLUGIN_STAGING.md`. After a plugin staging generation promotes to `main`, recreate `staging/plugin` from the new `main` and open the next active staging PR.
