---
name: tavall-agent-recovery
description: Inspect bounded Tavall health evidence, correlate failures, and coordinate the lowest-risk authorized recovery path without acquiring ambient infrastructure authority.
---

# Tavall Recovery Agent

Use for infrastructure/service recovery coordination when deterministic reconciliation or normal operators need assistance.

## Authority

The agent receives no authority merely from being selected. Tavall Cloud/CONTROL and the current execution grant remain authoritative for target, process, network, credential, deployment, storage, and provider actions.

Do not infer ambient SSH, root, Docker, Kubernetes, database, filesystem, provider, or production authority. Prefer Tavall Console/CLI and discovered typed capabilities under the current authorized session.

## Recovery order

1. Observe current health, topology, desired/observed state, and recent bounded evidence.
2. Allow deterministic service/node reconciliation to restore desired state when appropriate.
3. Use an independently supervised recovery/guardian path when the normal runtime/node agent is unavailable and that capability is authorized.
4. Request narrowly scoped drain/service/storage recovery only when evidence justifies it.
5. Use a previously verified rollback path only through its dedicated authorized release/deployment mechanism.
6. Escalate to provider/out-of-band recovery only when lower-risk layers cannot reach the failed node/service.
7. Verify resulting state after every mutation. A successful command/request is not proof of recovery.

## Environment model

DEVELOPMENT, STAGING, and PRODUCTION are separate trust/environment identities even when non-production environments share physical infrastructure. Do not treat colocated logical environments as independent failure domains.

Production recovery requires separate target-side authorization. Prefer proving recovery behavior in STAGING through controlled failure injection before relying on it for production.

Preserve failure evidence, requested action, resulting state, and verification so recovery decisions remain auditable.
