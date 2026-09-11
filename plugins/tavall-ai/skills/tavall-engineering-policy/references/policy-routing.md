# Policy Routing Hints

This file is a discovery aid, not a substitute for the live `tavall-docs` directory or `Tavall-Architecture-Tests`. File names, test coverage, and exact policy can evolve. Discover current sources before relying on this map.

## Always start here for material Tavall code work

- `TavallStudios/tavall-docs/docs/quality/CODE_ARCHITECTURE.md`
- target repository/module `AGENTS.md` / `AGENT.MD` and checked-in design rules
- `TavallStudios/Tavall-Architecture-Tests/AGENTS.md`
- `TavallStudios/Tavall-Architecture-Tests/README.md`
- architecture-test `manifest/sources.json` when canonical tests may apply

## Concern-to-source hints

| Concern detected in the requested or discovered work | Tavall Docs sources to discover/read | Architecture-test action |
| --- | --- | --- |
| class size, class roles, naming, package ownership | `CODE_ARCHITECTURE.md`, `CLASSES.md`, `NAMESPACES_VARIABLES_AND_OOP.md` | find tests enforcing names/packages/class ownership/size for the target or closest canonical owner |
| DI, composition, lifecycle owner, bootstrap | `CODE_ARCHITECTURE.md`, `DEPENDENCY_INJECTION_AND_ORCHESTRATION.md` | find DI/composition/lifecycle architecture tests before changing injection or ownership patterns |
| handlers/services/orchestrators/routers/builders | `CODE_ARCHITECTURE.md`, `HANDLERS.md`, `BUILDERS.md`, relevant orchestration guidance | find role/constructor/dependency tests that govern the touched type |
| interfaces, abstraction boundaries, API contracts | `CODE_ARCHITECTURE.md`, `INTERFACES_AND_ABSTRACTIONS.md`, request/result guidance | find interface/API/module-boundary tests |
| persistence/entities/database | `CODE_ARCHITECTURE.md`, `ENTITY_PERSISTENCE.md` | find persistence/repository/entity architecture tests; inspect checked-in Tavall Database contract too |
| caches, registries, mutable keyed state | `CODE_ARCHITECTURE.md`, `REGISTRIES_CACHES_AND_REPOSITORIES.md`, `APPLICATION_OWNED_MUTABLE_MAPS.md` | find cache/registry/mutable-state tests |
| concurrency, async work, thread/scheduler ownership | `CODE_ARCHITECTURE.md` plus the current concurrency/anti-pattern chapter discovered live | find direct-thread/executor/lifecycle tests and inspect current Tavall concurrency API |
| validation, mutation order, fallback, retry, reconciliation | `CODE_ARCHITECTURE.md`, `EFFECT_SEQUENCES.md`, current validation/fallback chapter | find fallback/mutation/effect-order tests where present |
| methods, requests, results, resolvers, formatters | `METHODS.md`, `REQUESTS_RESULTS_RESOLVERS_AND_FORMATTERS.md`, `CODE_ARCHITECTURE.md` | find signature/type-flow tests where present |
| shared architecture rule itself is changing | shared policy entry point plus every affected detailed chapter | update canonical architecture test and shared docs in the same review graph when both authorities change |

## Test-selection rules

1. Prefer direct canonical tests for the target repository/rule.
2. Verify source/provenance through the current architecture-test manifest when relevant.
3. Treat consumer copies as mirrors unless current canonical guidance says otherwise.
4. An analogous test from another repository can illuminate intent but is not proof that the target repository executed that test.
5. No direct canonical test is a coverage fact, not permission to invent one inside the feature PR without deciding canonical ownership first.
6. Repository-local regression/integration/acceptance tests still apply even when a canonical architecture test exists.

## Non-Tavall explicit adoption

When the user explicitly applies this skill to another project, select the Tavall rules that address the requested concerns and label them as adopted guidance for that task. Preserve mandatory target-repository rules and do not require Tavall infrastructure solely because the guidance originated in Tavall.
