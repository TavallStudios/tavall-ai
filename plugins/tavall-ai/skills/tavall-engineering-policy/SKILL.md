---
name: tavall-engineering-policy
description: Automatically use for Tavall coding, debugging, refactoring, testing, review, build/configuration, infrastructure-code, runtime-behavior, and architecture work to resolve the smallest current Tavall Docs + Tavall Architecture Tests policy set before material decisions. Use tavall-documentation-context for selective documentation lookup; never preload the full docs tree.
---

# Tavall Engineering Policy

Use this skill as the engineering concern and policy resolver. It does not copy Tavall architecture rules or document-routing tables into the skill.

## Activation

For Tavall-owned repository work, activate automatically when the task can materially affect source, tests, build logic, configuration, infrastructure code, runtime behavior, architecture, or code review. Natural-language requests are enough.

For a non-Tavall project, activate only when the user explicitly asks to apply Tavall architecture, Tavall engineering standards, `tavall-docs`, `Tavall-Architecture-Tests`, or this skill.

## Authority

Resolve current authority in this order:

1. target repository/module `AGENTS.md` / `AGENT.MD`, checked-in design rules, tool contracts, and stricter local requirements;
2. selected current shared documentation from `TavallStudios/tavall-docs@main`, resolved through `tavall-documentation-context`;
3. applicable current canonical tests/guidance from `TavallStudios/Tavall-Architecture-Tests`;
4. current production code, dependency/tool APIs, and repository-owned tests that demonstrate the real integration boundary;
5. historical memory only as a lead.

Repository-local rules may strengthen shared policy. They may not silently weaken a binding shared rule.

## Resolution workflow

Before a material engineering decision or edit:

1. Identify the target repository/module, touched behavior, runtime/platform, and acceptance boundary.
2. Read the target repository/module instructions and inspect the production owner of the touched behavior.
3. Convert the concrete engineering concerns into a **small set of exact concern labels**. Use terms declared by the current `tavall-docs@main/docs/quality/DOCUMENT_ROUTING.yml` when they apply. Examples are labels such as `dependency injection`, `persistence`, `registry`, `effect sequence`, `interface boundary`, or `architecture` only when those concerns are actually present.
4. Invoke `tavall-documentation-context` with the user's explicit selectors plus those concern labels. Read only the selected shared and repository-local documents.
5. Inspect `TavallStudios/Tavall-Architecture-Tests` only for rule families that can govern the touched concern. Find direct canonical tests for the target/rule when they exist and read the actual test source before changing governed behavior.
6. If no direct canonical architecture test exists for the target/rule, record `NO_DIRECT_CANONICAL_TEST`. Analogous tests may illuminate intent but are not proof of coverage.
7. Produce a compact policy set: selected authorities, required invariants, prohibited patterns, required tests/validation, and unresolved conflicts/gaps.
8. Hand that policy set to the active specialist. Re-run steps 3–7 only if the work expands into a new governed concern.

Do not start every code task by reading `CODE_ARCHITECTURE.md`. Do not recursively read `docs/quality`. Do not load Git workflow, documentation lifecycle, templates, or unrelated architecture chapters unless the resolver selects them.

## Architecture-test behavior

`Tavall-Architecture-Tests` is the canonical executable/reference layer for Tavall architecture tests. Canonical tests do not replace human-readable policy and they are not automatically relevant to every source change.

When an intentional shared architecture rule changes, coordinate its owning `tavall-docs` policy and applicable canonical architecture-test change in the same review graph. Migration-only work must not casually rewrite canonical test semantics.

## Scope signals

Re-resolve policy when the work newly crosses into a routed concern such as ownership/naming, DI/composition/lifecycle, persistence/state authority, concurrency, API/module boundaries, effect sequencing/fallback/retry, or an explicit shared architecture-rule change.

Ordinary edits inside an already resolved concern reuse the current policy context.

## Handoff

This skill owns engineering concern identification and policy applicability, not implementation or documentation mutation.

- `tavall-documentation-context` owns selective document discovery.
- `tavall-agent-implementation` implements within the resolved policy set.
- `tavall-agent-review` independently checks the exact head against it.
- `tavall-agent-architecture` owns explicitly approved structural migrations/repairs.
- `tavall-agent-documentation` edits documentation when an accepted change requires it.
- `tavall-git-workflow` owns Git/PR/staging/promotion policy.
- `tavall-local-ci` owns exact-head completion validation when a diff exists.

Retain enough evidence to state which documents and canonical tests actually governed the change. The absence of unrelated documentation from the context is expected, not a preflight failure.
