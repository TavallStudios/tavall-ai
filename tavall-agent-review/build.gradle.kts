val tavallJavaToolsVersion = "1.0.0"

repositories {
    val githubToken = providers.environmentVariable("GITHUB_TOKEN").orNull
    if (!githubToken.isNullOrBlank()) {
        listOf(
            "tavall-cache",
            "tavall-database",
            "tavall-eventbus",
            "tavall-reflection",
            "tavall-scheduler",
        ).forEach { repository ->
            maven("https://maven.pkg.github.com/TavallStudios/$repository") {
                name = "review${repository.replace("-", "")}"
                credentials {
                    username = providers.environmentVariable("GITHUB_ACTOR").orElse("github").get()
                    password = githubToken
                }
            }
        }
    }
}

dependencies {
    implementation("org.tavall:abstract-cache-semantic:$tavallJavaToolsVersion")
    runtimeOnly("org.tavall:abstract-cache-storage-memory:$tavallJavaToolsVersion")
    implementation("org.tavall:tavall-concurrency:$tavallJavaToolsVersion")
    implementation("org.tavall:tavall-database-core-contracts:$tavallJavaToolsVersion")
    implementation("org.tavall:tavall-eventbus:$tavallJavaToolsVersion")
    implementation("org.tavall:tavall-logging:$tavallJavaToolsVersion")
    implementation("org.tavall:tavall-reflection:$tavallJavaToolsVersion")
    implementation("org.tavall:tavall-registry:$tavallJavaToolsVersion")
    implementation("org.tavall:tavall-scheduler:$tavallJavaToolsVersion")
}
