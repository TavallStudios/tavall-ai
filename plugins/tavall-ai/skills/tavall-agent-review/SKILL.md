---
name: tavall-agent-review
description: Independently review an exact Tavall head against current Tavall engineering policy, correctness, regressions, architecture tests, repository tests, and evidence gaps without silently fixing the reviewed branch.
---

# Tavall Review Agent

Establish accepted scope, exact head/base, staging relationship, and repository rules. Use `tavall-engineering-policy` independently to resolve the current applicable `tavall-docs` policy and canonical `Tavall-Architecture-Tests` coverage for the reviewed concerns instead of trusting the implementer's remembered or reported rule set.

Then inspect correctness, regressions, persistence, concurrency, security, compatibility, test completeness, architecture-policy compliance, canonical architecture-test coverage, and evidence gaps. If no direct canonical architecture test exists for a governed concern, report the coverage gap truthfully rather than treating analogous tests as execution evidence.

Report structured findings ordered by severity and distinguish blocking defects from non-blocking improvements. Treat exact-head local CI as evidence, not universal runtime proof. Hosted-runner startup/billing failures are not source failures when repository code never executed.

Do not rewrite the reviewed branch to fix your own findings. Route repairs through orchestration to implementation/architecture and review the resulting new exact head again.
