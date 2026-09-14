# Tavall AI Memory Runtime

> **Status:** Runtime-owned memory authority extracted from the legacy AgentTaskManager memory plane. Provider adapter migration and exact-head DEVELOPMENT acceptance remain pending.

## Ownership

`tavall-ai-runtime-memory` is a Tavall AI runtime capability module, not an agent and not an MCP/tool package.

It owns provider-neutral memory semantics:

- explicit durable-memory writes;
- GLOBAL / PROJECT / SESSION authority scopes;
- stable memory identity and deterministic lock ordering;
- same-scope supersession rules;
- canonical record + semantic-outbox transaction ordering;
- post-commit exact-state cache refresh and GLOBAL revision invalidation;
- exact-state + semantic + structural/temporal hydration;
- provider degradation without losing canonical exact state.

## Authority boundaries

### Tavall AI memory runtime

The runtime validates memory authority and lifecycle semantics. `TavallMemoryWriteRequest` intentionally has no caller-selectable consent/write-authority field. The only agent-authored durable write operation is explicit and the runtime persists `writeAuthority=explicit` / `metadata.writeMode=explicit` through the storage adapter.

Supersession remains constrained by authenticated user/workspace authority:

- GLOBAL may cross projects only within the same user/workspace;
- PROJECT requires the same project;
- SESSION requires the same project, chat, and thread;
- replacement scope must equal existing scope;
- only active, non-tombstoned, non-superseded records may be replaced.

### Storage adapters

The runtime defines ports; infrastructure adapters implement them.

`TavallMemoryRecordStore` / `TavallMemoryTransaction` is the canonical durable transaction boundary, normally backed by Postgres. An implementation must:

1. hold stable-identity locks until commit or rollback;
2. acquire multiple stable identities in the order supplied by the runtime;
3. atomically commit canonical memory mutation and semantic outbox mutation;
4. return only records committed by that authority.

The legacy Postgres implementation used transaction-scoped advisory locks. A migrated Postgres adapter must preserve that behavior rather than replacing it with process-local locking.

`TavallMemoryHotStateStore` is the Redis-style exact-state/cache-revision boundary. GLOBAL writes advance the user/workspace revision after canonical commit so previously primed project caches become unreachable without key scans.

`TavallMemorySemanticStore` is semantic **retrieval only**. Semantic writes/deletes enter through the canonical transaction outbox, preventing Qdrant or another vector provider from accepting state that Postgres did not commit.

`TavallMemoryKnowledgeProvider` is the optional structural/temporal provider boundary for Graphify, Graphiti, and future context providers. Provider failure degrades its context independently; it does not erase exact canonical memory.

### Tavall Cloud / runtime host

The host owns database/cache/vector/provider credentials, network/process authority, placement, and secrets. This module does not discover or mint infrastructure access.

### Function Catalog

Function Catalog owns callable typed projections such as `memoryContext`, `recordMemory`, semantic investigation, or temporal-fact operations when those capabilities are exposed to an execution. Tavall AI does not maintain a second MCP schema/tool-handler implementation for memory.

### Unified Tavall plugin

Portable memory procedures and client-facing skill guidance belong to the unified Tavall plugin. The old `.agents/skills/tavall-memory-*` copies from the AgentTaskManager lane are migration input only and are not carried into this runtime PR.

## Retrieval

`TavallMemoryRetrievalService` is the single structured provider-neutral hydration path:

1. load exact state using a cache key that includes the user/workspace GLOBAL revision;
2. perform bounded semantic retrieval when a project/query is available;
3. rank semantic results using semantic score, recency, importance, and memory scope;
4. ask optional structural/temporal knowledge providers;
5. return structured `TavallMemoryHydration`.

Prompt text formatting and model-provider projection happen outside this module.

## GLOBAL semantic limitation

GLOBAL exact-state memory remains cross-project by canonical storage authority. The legacy implementation mirrored a GLOBAL record into the originating project semantic namespace and tracked that namespace for deterministic supersession cleanup. The extracted runtime preserves that compatibility contract through record metadata/outbox namespace selection.

A dedicated cross-project GLOBAL semantic namespace is still future work. It should use a user/workspace namespace and reuse a query embedding rather than adding another independent embedding pass.

## Legacy AgentTaskManager mapping

The following are deliberately **not** migrated into this module:

- Spring application/service/controller ownership;
- `agent_task_manager.*` schema naming as a Java API concept;
- legacy MCP request/handler/catalog classes;
- old desktop operation catalog;
- prompt auto-capture/writeback behavior;
- `.agents/skills/tavall-memory-*` instruction copies;
- old deployment/systemd scripts;
- seed/import/re-embedding operational scripts unless separately justified by a provider migration.

The legacy branch remains in Git ancestry for provider-adapter and data-migration reference.

## Acceptance still required

Before this migration can replace the old DEVELOPMENT memory deployment:

- Java 25 exact-head unit/repository verification;
- a current Postgres adapter preserving advisory transaction locks and durable semantic outbox behavior;
- a current Redis adapter preserving GLOBAL revision coherence;
- current semantic adapter/outbox drain acceptance against Qdrant or its successor;
- Graphify and Graphiti provider adapters through authorized host/Function Catalog surfaces;
- concurrency tests against real Postgres for stable writes and cross-supersession;
- restart/durability and provider-outage acceptance;
- proof that ordinary turns do not create durable memory;
- Function Catalog typed memory functions bound to this runtime rather than legacy MCP handlers;
- exact-head independent review and DEVELOPMENT deployment evidence.
