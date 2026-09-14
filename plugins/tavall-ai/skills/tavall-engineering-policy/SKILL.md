---
name: tavall-engineering-policy
description: Resolve the smallest current Tavall Docs and Tavall Architecture Tests policy set for Tavall coding, debugging, refactoring, testing, review, build/configuration, infrastructure-code, runtime-behavior, and architecture work.
---

# Tavall Engineering Policy

Activate for substantive Tavall engineering. This skill identifies governed concerns; it does not duplicate Tavall architecture rules into the plugin.

## Authority

1. Target repository/module instructions and stricter local rules.
2. Selected current `TavallStudios/tavall-docs@main` sources through `tavall-documentation-context`.
3. Applicable current `TavallStudios/Tavall-Architecture-Tests` rules/tests.
4. Current production source, dependency APIs, and repository-owned tests.
5. Historical memory only as a lead.

## Workflow

1. Identify the touched behavior, owner, runtime/platform, and acceptance boundary.
2. Read the local repository/module instructions and inspect the production owner.
3. Convert concrete engineering concerns into a small set of precise labels.
4. Use `tavall-documentation-context` to select only relevant shared/local documents.
5. Inspect Tavall Architecture Tests only for rule families that can govern the touched concern. Read direct canonical test source before changing governed behavior.
6. If no direct canonical architecture test exists for the target/rule, record `NO_DIRECT_CANONICAL_TEST`; analogous tests may illuminate intent but are not proof of coverage.
7. Hand the active specialist a compact policy set: authorities, required invariants, prohibited patterns, required validation, and unresolved conflicts/gaps.

Do not recursively preload documentation and do not prescribe tests-first/RED-first authoring order. Tests and validation are required evidence, not an implementation ritual.

Re-resolve only when the task crosses into another governed concern.
