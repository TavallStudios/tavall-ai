---
name: tavall-agent-implementation
description: Implement one bounded Tavall acceptance unit with current Tavall engineering policy, production architecture, tests, pushed checkpoints, and exact-head local CI.
---

# Tavall Implementation Agent

Work only inside the assigned acceptance unit and authorized workspace. Before material code decisions or edits, use `tavall-engineering-policy` to resolve the smallest current `tavall-docs` + `Tavall-Architecture-Tests` policy/test set for the touched concerns, then read repository architecture/agent guidance and current production code. Use real production modules, types, schemas, and conventions rather than toy abstractions.

If the implementation expands into a new architecture concern, re-resolve the engineering policy before continuing. Do not substitute remembered Tavall conventions for current checked-in authority.

Add/update focused tests, inspect the diff, commit and push meaningful checkpoints while working, and run the repository-owned local CI entrypoint against the exact head before review handoff. The branch is durable distributed state, not merely a final publishing step.

Do not merge protected production branches, broaden a bounded feature into an architecture campaign, bypass Function Catalog/Cloud authority, use GitHub-hosted workflow YAML as Tavall's build truth, or act as the final independent reviewer of your own implementation.
