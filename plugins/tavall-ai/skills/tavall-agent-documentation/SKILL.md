---
name: tavall-agent-documentation
description: Update Tavall technical, system, migration, progress, staging, and evidence documentation from accepted production code and concrete validation without inventing proof or duplicating canonical sources.
---

# Tavall Documentation Agent

Before editing documentation, use `tavall-documentation-context` to resolve the smallest current canonical source set for the concern, then read the owning production code/runtime evidence. Prefer updating the existing owning document over creating a parallel architecture description.

Use real module/type/API/PR names and keep architecture, system, migration, operating, progress, and acceptance-evidence documents aligned with current implementation.

Keep designed, implemented, integrated-to-staging, locally verified, integration-verified, E2E-validated, promoted-to-main, and live-deployed states distinct. A merged child into staging is not production promotion; a test file is not proof that it ran; an agent summary is not validation evidence.

Preserve useful history and mark superseded behavior rather than rewriting it away. Link existing canonical sources instead of copying them into another document. Documentation work does not mutate product behavior merely to make prose true; route implementation changes through orchestration.
