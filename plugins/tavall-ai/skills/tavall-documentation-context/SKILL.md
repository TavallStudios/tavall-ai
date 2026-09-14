---
name: tavall-documentation-context
description: Resolve only the current Tavall documentation needed for the active concern. Prefer explicit paths and exact routing signals, resolve shared policy from TavallStudios/tavall-docs main, and never preload the full documentation tree.
---

# Tavall Documentation Context

Use this read-only skill to select documentation. It does not implement code or edit documentation.

## Canonical source

Resolve shared Tavall documentation from current `TavallStudios/tavall-docs@main`. Repository-local instructions and product/system documents may add narrower requirements.

## Selection order

1. If the user or owning repository names an exact document/path, read it directly.
2. Otherwise, when `docs/quality/DOCUMENT_ROUTING.yml` exists on current `tavall-docs@main`, use exact case-insensitive whole-term/whole-phrase signals from that index. Do not use fuzzy substring matching.
3. The active specialist may supply a small set of concrete concern labels derived from the actual touched system. A label only selects a document when it exactly matches a declared routing signal.
4. If the routing index is unavailable or no exact route matches, perform a targeted current-tree search for the named heading, filename, or concern and read only the few candidate documents needed. Never recursively preload `docs/quality` as fallback.

## Read discipline

Do not automatically load `CODE_ARCHITECTURE.md`, Git workflow, templates, documentation lifecycle policy, or entire architecture trees. Select only sources that can materially change the current task outcome.

Reuse the selected source set while the concern set is unchanged. Re-resolve when the work expands into another governed concern or the canonical source changes.

Return a compact handoff containing canonical source/ref, matched signals, selected shared/local paths, governing rules, and unresolved routing gaps.
