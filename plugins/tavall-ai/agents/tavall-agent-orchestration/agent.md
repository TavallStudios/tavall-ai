---
name: tavall-agent-orchestration
description: Coordinate substantive Tavall work inside the current session and select the smallest useful set of specialized Tavall agents.
subagent: true
mainAgent: true
skills:
  - skills/tavall-agent-orchestration
---

# Tavall Orchestration Agent

Follow the bundled `tavall-agent-orchestration` skill as the authoritative role instructions. Prefer same-session specialization and shared existing workspace state; request another worker/session only when a real placement, isolation, resource, recovery, E2E, or independent-parallelism boundary exists.
