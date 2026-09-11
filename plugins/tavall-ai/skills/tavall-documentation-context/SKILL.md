---
name: tavall-documentation-context
description: Resolve and read only the current Tavall documentation needed for a task. Use when a Tavall prompt, code concern, review finding, or agent reasoning names or implies a documentation-governed topic. Prefer explicit paths and exact whole-word/phrase signals, always resolve shared policy from TavallStudios/tavall-docs main, and never preload the full documentation tree.
---

# Tavall Documentation Context Resolver

Use this skill as the shared read-only documentation discovery boundary for Tavall work. It selects documentation; it does not implement code and it does not edit documentation by itself.

## Canonical source

Shared Tavall documentation must be resolved from the current `main` branch of `TavallStudios/tavall-docs` at lookup time.

Do not use a consumer repository's copied pointer, pinned historical blob, cached document body, memory summary, or stale skill reference as a substitute for the current shared source. Repository-local instructions and product/system documents may add narrower requirements, but shared Tavall policy remains live from `tavall-docs@main`.

## Selection order

Select the smallest source set that can materially change the task outcome.

1. **Explicit document path/name.** If the user or owning repository names an exact `tavall-docs` path or filename, read that document directly from `main`. Do not load the routing index merely to rediscover an already explicit path.
2. **Exact user-prompt signal.** Otherwise read only `docs/quality/DOCUMENT_ROUTING.yml` from `tavall-docs@main`. Match the user's words against declared signals as case-insensitive whole terms or whole phrases. Listed aliases are explicit; substring/fuzzy similarity is not a match.
3. **Exact agent-reasoning signal.** If the prompt has no direct signal, the active specialist may emit a small set of concrete concern labels derived from the actual touched code/system, such as `dependency injection`, `persistence`, or `effect sequence`. A reasoning label must exactly match a signal declared by the routing index before it selects a document.
4. **Targeted search fallback.** If neither prompt nor reasoning labels resolve a route, search the current `tavall-docs` tree for the named filename, heading, or concept. Read only the matching candidate documents needed to resolve the ambiguity. Never fall back to recursively reading `docs/quality`.

Explicit prompt matches and reasoning matches may be combined when both genuinely apply. Deduplicate the selected document list.

## Read discipline

- Read only selected documents. Do not preload `docs/quality/README.md`, `CODE_ARCHITECTURE.md`, `GIT_WORKFLOW.md`, templates, or the full `code-architecture/` directory by default.
- A detailed architecture chapter can govern a task without also loading `CODE_ARCHITECTURE.md`.
- Load `CODE_ARCHITECTURE.md` when the routing index selects cross-cutting architecture policy or another selected source explicitly requires it.
- Load document templates only when creating or materially restructuring that document type.
- Load Git workflow policy only for routed Git/branch/PR/staging/promotion/release/deployment concerns.
- Repository `AGENTS.md` / `AGENT.MD` remains an instruction source. It does not authorize recursively loading that repository's documentation tree.
- Select repository-local design/system/progression/migration/operating documents only when the explicit prompt, touched system, or a concrete reasoning label makes that local document relevant.

## Scope changes

Reuse the already resolved source set while the concern set is unchanged. Re-resolve only when:

- the user names a different document or policy concern;
- the work expands into a new concern represented by another exact routing signal; or
- a previously selected canonical document changes during the task.

A branch-head change by itself is not a reason to reread unrelated documentation.

## Handoff contract

Return a compact documentation context to the caller containing:

- canonical source: `TavallStudios/tavall-docs@main` and the resolved commit when available;
- matched prompt signals;
- matched reasoning signals, if any;
- selected shared document paths;
- selected repository-local document paths, if any;
- unresolved routing gaps, if any.

Do not paste entire documents into the handoff unless the caller actually needs their full contents. Extract the governing rules or relevant sections and retain source paths for verification.

`tavall-agent-documentation` uses this resolver before documentation mutation. Engineering, architecture, implementation, review, E2E, and reconciliation agents may use it directly whenever their current reasoning reaches a documentation-governed concern.
