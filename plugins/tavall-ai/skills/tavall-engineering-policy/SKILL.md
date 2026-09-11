---
name: tavall-engineering-policy
description: Automatically use for Tavall coding, debugging, refactoring, testing, review, build/configuration, infrastructure-code, and architecture work to resolve the smallest current Tavall Docs + Tavall Architecture Tests policy set before decisions or edits. Also use when the user explicitly asks to apply Tavall engineering standards to a non-Tavall project; never auto-apply Tavall policy outside Tavall without that explicit request.
---

# Tavall Engineering Policy

Use this skill as the shared engineering-policy resolver. It does **not** copy Tavall architecture rules into the skill. It determines which current canonical rules and tests apply to the task, loads those sources, and hands the resolved policy set to implementation, review, architecture, E2E, or other domain specialists.

## Activation modes

### Tavall automatic mode

Activate automatically for Tavall repository-backed engineering work that can affect source, tests, build logic, configuration, infrastructure code, runtime behavior, architecture, or code review. Natural-language requests are enough; the user does not need to name this skill or use Tavall-specific terminology.

Examples include "fix this bug", "finish this PR", "clean this up", "add this feature", "why is this failing", "review this", "port this module", and "make this production ready" when the target is Tavall-owned work.

### Explicit external-adoption mode

For a non-Tavall project, activate only when the user explicitly asks to use Tavall architecture, Tavall engineering standards, this skill, `tavall-docs`, or `Tavall-Architecture-Tests` as guidance.

External adoption does not silently make the project a Tavall repository. Do not impose Tavall Git topology, Tavall CI, Tavall runtime dependencies, or Tavall-specific platform choices unless the user separately asks for them or the target project already consumes them. Apply the requested engineering rules as a selected policy profile while preserving the target repository's own mandatory instructions.

## Canonical authorities

For Tavall work, resolve current authority in this order:

1. Target repository/module `AGENTS.md` / `AGENT.MD`, checked-in design rules, tool contracts, and stricter local requirements.
2. `TavallStudios/tavall-docs`, beginning with `docs/quality/CODE_ARCHITECTURE.md` for code/architecture work and adding the relevant detailed chapters under `docs/quality/code-architecture/`.
3. `TavallStudios/Tavall-Architecture-Tests`, including `AGENTS.md`, `README.md`, `manifest/sources.json`, and applicable canonical test sources under `repositories/`.
4. Current production code, current checked-in dependency/tool APIs, and repository-owned tests that show the actual integration boundary.
5. Historical memory only as a lead. Never let remembered architecture override current checked-in sources.

Repository-local rules may strengthen shared Tavall policy. They may not silently weaken a binding shared rule. When two current authorities appear to conflict, surface the conflict and resolve it from the narrower explicit owner or the shared document's own precedence rules before editing.

## Policy-resolution workflow

Before material engineering decisions or edits:

1. Identify the target repository, module/package, affected runtime/platform, requested behavior, and expected acceptance boundary.
2. Read the target repository/module instructions and the current production owner of the touched behavior.
3. Read the current Tavall Docs architecture entry point plus only the detailed chapters relevant to the touched concerns. Use `references/policy-routing.md` as discovery hints, not as a frozen rule inventory.
4. Inspect the current Tavall Architecture Tests guidance and manifest.
5. Find canonical tests that directly cover the target repository/rule when they exist. Read the actual test source before changing the governed behavior.
6. If no direct canonical architecture test exists for the target/rule, record that as `NO_DIRECT_CANONICAL_TEST`; use shared docs plus repository-local tests and analogous canonical tests only as reference. Never claim an architecture test passed when no applicable executable test exists.
7. Produce a compact internal policy set: applicable authorities, required invariants, prohibited patterns, required tests/validation, and unresolved conflicts/gaps.
8. Hand that policy set to the selected specialist and re-resolve it if the work expands into a new architecture domain.

Do not preload every Tavall document for every typo fix. Resolve the smallest policy set that can change the implementation or review outcome.

## Architecture-test behavior

`Tavall-Architecture-Tests` is the canonical executable/reference layer for Tavall architecture tests. Files under its `repositories/` tree are canonical sources; consumer copies are compatibility mirrors unless current repository guidance says centralized execution is already wired.

When an intentional architecture-rule change is in scope, do not patch only the consumer. Coordinate the canonical architecture test change and the matching `tavall-docs` policy/design update in the same review graph when those authorities must change.

Migration-only work must not casually rewrite canonical test semantics while relocating or synchronizing them. Preserve source provenance rules from the architecture-test repository.

## Scope signals that require re-resolution

Re-run policy resolution when work crosses into any newly affected concern such as:

- class/package ownership or naming;
- dependency injection/composition;
- lifecycle/bootstrap/unload behavior;
- persistence, cache, registry, or distributed state authority;
- concurrency/scheduling/thread ownership;
- request/result/API/module boundaries;
- validation, fallback, partial-failure, retry, or reconciliation semantics;
- build/runtime dependency changes;
- architecture-test or shared-policy changes.

## Handoff to specialists

This skill owns policy discovery and applicability, not feature implementation or final review.

- `tavall-agent-implementation` implements within the resolved policy set.
- `tavall-agent-review` independently checks the exact head against it.
- `tavall-agent-architecture` owns explicitly approved structural migrations/repairs and must still use this policy resolver first.
- `tavall-git-workflow` owns Tavall Git/PR/staging policy.
- `tavall-local-ci` owns exact-head Tavall completion validation when a diff exists.

A specialist must not substitute remembered Tavall conventions for the resolved current policy set.

## Completion evidence

For Tavall engineering work, retain enough evidence to state:

- which canonical documents/chapters materially governed the change;
- which canonical architecture tests were applicable, or that no direct canonical test existed;
- which repository-local rules/tests strengthened the shared policy;
- whether an authority conflict or coverage gap remains;
- which exact-head validation/review path accepted the resulting code when the task produced a diff.
