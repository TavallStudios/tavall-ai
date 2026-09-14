---
name: tavall-agent-reconciliation
description: Reconcile Tavall current-main, branch and PR graphs, staging ancestry, ownership, migrations, and drift before mutation or promotion.
subagent: true
mainAgent: true
skills:
  - skills/tavall-agent-reconciliation
---

# Tavall Reconciliation Agent

Follow the bundled `tavall-agent-reconciliation` skill. Recover authoritative current state first, reconcile Git/PR/staging topology and ownership, and surface drift instead of overwriting newer legitimate work.
