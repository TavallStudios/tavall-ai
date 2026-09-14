---
name: tavall-agent-builder
description: Coordinate Tavall Builder/Minecraft build work around the authoritative Project Novus Builder platform, including concept generation, deterministic Builder Studio evidence, visual critique, semantic repair, and explicit live certification.
---

# Tavall Builder Agent

Use this coordinator for Tavall Builder jobs. It does not own Minecraft geometry, palettes, schematic formats, simulation, rendering, or a second AI runtime.

## Authoritative Builder implementation

When Project Novus Builder sources are present, treat `minecraft-bot-builder` and `minecraft-bot-builder/skills/minecraft-builder/` as authoritative. Do not duplicate BuildSpec logic, palettes, schematic serialization, world-vision/mock/replay logic, Builder Studio rendering, FAWE placement, Mineflayer traversal, verification agents, or repair-learning logic inside Tavall AI.

## Default orchestration

For a new build or substantial redesign, preserve the authoritative Builder flow when available:

`intent/references/constraints -> Builder context/palette -> concept generation -> inert BuildSpec -> deterministic compile/mock/world-vision validation -> Builder Studio evidence -> visual/gameplay critique -> bounded semantic repair -> re-render/re-verify -> live certification when required`

Planner, Terrain, Architecture, Detail, Repair, and Visual Critic behaviors may enrich constraints and repairs, but should not replace the authoritative Builder contract with an improvised parallel pipeline.

## Model and deterministic work

Use the parent runtime/distributed-execution surface only for genuinely model-shaped planning, critique, or semantic repair. Keep deterministic voxel execution, lowering, compilation, validation, replay, artifact generation, and verification local to the owning Builder implementation.

Preserve concept/source provenance, seed, palette, constraints, artifact identity, and evidence across repair iterations. Repairs should modify the accepted source, not silently replace it with an unrelated build.

## Review and simulation

Visual review should cover recognizability, true 3D structure, prompt fidelity, scale/proportion, detail, composition, gameplay readability, traversal, encounter fairness, palette cohesion, world context, performance/density, and production validity.

When Builder Studio execution is authorized, use typed simulation requests and keep artifacts/evidence inside the authorized environment component. Prefer deterministic Studio/replay evidence for iteration. Generated images, imagined screenshots, or prose-only visual claims are not Builder evidence.

Live Paper + FAWE + Mineflayer remains a later certification boundary when the owning Builder workflow requires it.

## Source/environment identity

For substantive Builder engineering, bind the exact multi-repository source snapshot to the Tavall lane/environment generation used for implementation and acceptance. If a participating source head changes materially, prior environment validation becomes historical and the matching generation/evidence must be refreshed.

Tavall Cloud/runtime host remains authoritative for source snapshots, workspace/process/network, executable/credential, environment components, and target mutation. Builder Studio never grants production-world authority.
