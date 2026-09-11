plugins {
    base
}

group = "org.tavall.ai"
extra["versionTagPrefix"] = "tavall-ai"
extra["fallbackVersion"] = "0.1.0"
apply(from = "gradle/git-version.gradle.kts")
version = extra["gitVersion"] as String

val junitVersion = "5.12.2"
val agentProviderService = "org.tavall.agent.TavallAgentProvider"
val moduleProviderService = "org.tavall.ai.bootstrap.TavallAIModuleProvider"
val agentProjects = listOf(
    "tavall-agent-scheduler",
    "tavall-agent-orchestration",
    "tavall-agent-implementation",
    "tavall-agent-review",
    "tavall-agent-reconciliation",
    "tavall-agent-e2e",
    "tavall-agent-architecture",
    "tavall-agent-documentation",
    "tavall-agent-builder",
)
val runtimeModuleProjects = listOf(
    "tavall-ai-runtime-distributed-execution",
)

subprojects {
    group = rootProject.group
    version = rootProject.version

    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    extensions.configure<JavaPluginExtension> {
        toolchain.languageVersion = JavaLanguageVersion.of(25)
        withSourcesJar()
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        "testImplementation"(platform("org.junit:junit-bom:$junitVersion"))
        "testImplementation"("org.junit.jupiter:junit-jupiter")
        "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<JavaCompile>().configureEach {
        options.release = 25
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        maxParallelForks = 1
        maxHeapSize = "256m"
    }

    tasks.withType<Jar>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }

    extensions.configure<PublishingExtension> {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                artifactId = project.name
            }
        }
        repositories {
            val token = providers.environmentVariable("GITHUB_TOKEN")
            if (token.isPresent) {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/TavallStudios/tavall-ai-agent-task-manager")
                    credentials {
                        username = providers.environmentVariable("GITHUB_ACTOR").orNull
                        password = token.get()
                    }
                }
            }
        }
    }
}

configure(agentProjects.map(::project)) {
    dependencies {
        "api"(project(":tavall-ai-bootstrap"))
    }

    val verifyAgentDescriptor = tasks.register("verifyAgentDescriptor") {
        group = "verification"
        description = "Verifies this Tavall agent publishes one provider and one canonical ROLE.md."
        doLast {
            val serviceFile = file("src/main/resources/META-INF/services/$agentProviderService")
            check(serviceFile.isFile) {
                "Missing Tavall agent ServiceLoader registration for $path: $serviceFile"
            }
            val providers = serviceFile.readLines()
                .map(String::trim)
                .filter(String::isNotBlank)
                .filterNot { it.startsWith("#") }
            check(providers.size == 1) {
                "Expected exactly one Tavall agent provider for $path, found ${providers.size}"
            }

            val roleDocuments = fileTree("src/main/resources") {
                include("**/ROLE.md")
            }.files
            check(roleDocuments.size == 1) {
                "Expected exactly one canonical ROLE.md for $path, found ${roleDocuments.size}"
            }
            check(roleDocuments.single().readText().isNotBlank()) {
                "Canonical ROLE.md must not be blank for $path"
            }
        }
    }

    tasks.named("check") {
        dependsOn(verifyAgentDescriptor)
    }
}

configure(runtimeModuleProjects.map(::project)) {
    val verifyRuntimeModuleDescriptor = tasks.register("verifyRuntimeModuleDescriptor") {
        group = "verification"
        description = "Verifies this Tavall AI runtime capability module publishes exactly one module provider."
        doLast {
            val serviceFile = file("src/main/resources/META-INF/services/$moduleProviderService")
            check(serviceFile.isFile) {
                "Missing Tavall AI runtime-module ServiceLoader registration for $path: $serviceFile"
            }
            val providers = serviceFile.readLines()
                .map(String::trim)
                .filter(String::isNotBlank)
                .filterNot { it.startsWith("#") }
            check(providers.size == 1) {
                "Expected exactly one Tavall AI runtime module provider for $path, found ${providers.size}"
            }
        }
    }

    tasks.named("check") {
        dependsOn(verifyRuntimeModuleDescriptor)
    }
}

val stageDistribution = tasks.register<Sync>("stageDistribution") {
    group = "distribution"
    description = "Stages the Tavall AI runtime distribution and ChatGPT plugin as one inspectable release candidate."

    dependsOn(":tavall-ai-runtime:installDist")

    into(layout.buildDirectory.dir("stage/tavall-ai"))
    into("runtime") {
        from(project(":tavall-ai-runtime").layout.buildDirectory.dir("install/tavall-ai-runtime"))
    }
    into("plugins/tavall-ai") {
        from(layout.projectDirectory.dir("plugins/tavall-ai"))
    }
}

val verifyTavallAISystem = tasks.register("verifyTavallAISystem") {
    group = "verification"
    description = "Runs checks for Tavall AI bootstrap, agents, runtime modules, and runtimes."
    dependsOn(subprojects.map { it.tasks.named("check") })
}

tasks.named("check") {
    dependsOn(verifyTavallAISystem)
}

val verifySkillOrchestration = tasks.register("verifySkillOrchestration") {
    group = "verification"
    description = "Verifies Tavall skill routing, canonical policy sources, and engineering-policy activation contracts."

    doLast {
        fun requiredText(path: String): String {
            val source = file(path)
            check(source.isFile) { "Missing required skill-orchestration source: $path" }
            return source.readText()
        }

        val engineeringPolicyPath = "plugins/tavall-ai/skills/tavall-engineering-policy/SKILL.md"
        val engineeringPolicy = requiredText(engineeringPolicyPath)
        val policyRouting = requiredText("plugins/tavall-ai/skills/tavall-engineering-policy/references/policy-routing.md")
        val registry = requiredText("plugins/tavall-skill-orchestrator/registry.yaml")
        val orchestrator = requiredText("plugins/tavall-skill-orchestrator/SKILL.md")
        val orchestratorAgent = requiredText("plugins/tavall-skill-orchestrator/agents/openai.yaml")
        val pluginMetadata = requiredText("plugins/tavall-ai/.codex-plugin/plugin.json")
        val bundle = requiredText("skill-orchestration/bundle.yaml")
        val gitSkill = requiredText("plugins/tavall-git-workflow/SKILL.md")
        val gitPolicyPointer = requiredText("plugins/tavall-git-workflow/references/canonical-policy.md")

        check(engineeringPolicy.contains("name: tavall-engineering-policy")) {
            "$engineeringPolicyPath must expose the canonical skill identity"
        }
        check(engineeringPolicy.contains("Natural-language requests are enough")) {
            "$engineeringPolicyPath must explicitly support natural-language Tavall engineering activation"
        }
        check(engineeringPolicy.contains("TavallStudios/tavall-docs")) {
            "$engineeringPolicyPath must resolve shared policy from tavall-docs"
        }
        check(engineeringPolicy.contains("TavallStudios/Tavall-Architecture-Tests")) {
            "$engineeringPolicyPath must resolve canonical architecture-test coverage"
        }
        check(engineeringPolicy.contains("NO_DIRECT_CANONICAL_TEST")) {
            "$engineeringPolicyPath must preserve missing direct architecture-test coverage as evidence"
        }
        check(engineeringPolicy.contains("Explicit external-adoption mode")) {
            "$engineeringPolicyPath must keep non-Tavall adoption explicit"
        }
        check(policyRouting.contains("Concern-to-source hints")) {
            "Engineering-policy routing reference must preserve concern-based source discovery"
        }

        check(registry.contains("id: tavall-engineering-policy")) {
            "Skill registry must expose tavall-engineering-policy"
        }
        check(registry.contains("invocation: automatic_for_tavall_engineering")) {
            "Skill registry must auto-route Tavall engineering work"
        }
        check(registry.contains("external_invocation: explicit_only")) {
            "Skill registry must not auto-apply Tavall policy to external projects"
        }
        check(registry.contains("infrastructure_code")) {
            "Skill registry must route infrastructure-code changes through Tavall engineering policy"
        }
        check(bundle.contains("- tavall-engineering-policy")) {
            "Skill bundle must integrate tavall-engineering-policy"
        }
        check(orchestrator.contains("automatically require `tavall-engineering-policy`")) {
            "Orchestrator must automatically select Tavall engineering policy for natural coding work"
        }
        check(orchestratorAgent.contains("automatically require \$tavall-engineering-policy")) {
            "Installed orchestrator prompt must auto-select Tavall engineering policy"
        }
        check(pluginMetadata.contains("tavall-engineering-policy")) {
            "Tavall AI plugin metadata must advertise engineering-policy resolution"
        }

        listOf(
            "plugins/tavall-ai/skills/tavall-agent-implementation/SKILL.md",
            "plugins/tavall-ai/skills/tavall-agent-review/SKILL.md",
            "plugins/tavall-ai/skills/tavall-agent-architecture/SKILL.md",
            "plugins/tavall-ai/skills/tavall-agent-orchestration/SKILL.md",
            "plugins/tavall-ai/skills/tavall-ai/SKILL.md",
        ).forEach { path ->
            check(requiredText(path).contains("tavall-engineering-policy")) {
                "$path must route material Tavall engineering decisions through tavall-engineering-policy"
            }
        }

        val expectedGitPolicyRepository = "TavallStudios/tavall-docs"
        val staleGitPolicyRepository = "TavallStudios/tavall-project-novus"
        check(gitSkill.contains(expectedGitPolicyRepository) && gitPolicyPointer.contains(expectedGitPolicyRepository)) {
            "Git workflow skill and pointer must use tavall-docs as shared authority"
        }
        check(!gitSkill.contains(staleGitPolicyRepository) && !gitPolicyPointer.contains(staleGitPolicyRepository)) {
            "Git workflow skill still points at the retired Project Novus policy location"
        }

        val skillIdentities = fileTree("plugins") {
            include("**/SKILL.md")
        }.files.mapNotNull { skillFile ->
            skillFile.useLines { lines ->
                lines.firstOrNull { it.startsWith("name: ") }?.removePrefix("name: ")?.trim()
            }?.let { identity -> identity to skillFile }
        }
        val duplicates = skillIdentities.groupBy({ it.first }, { it.second })
            .filterValues { it.size > 1 }
        check(duplicates.isEmpty()) {
            "Duplicate Tavall skill identities detected: " + duplicates.keys.joinToString(", ")
        }
    }
}

tasks.named("check") {
    dependsOn(verifySkillOrchestration)
}
