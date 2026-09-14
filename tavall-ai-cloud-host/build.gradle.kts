dependencies {
    api(project(":tavall-ai-runtime-spi"))
    implementation(project(":tavall-ai-bootstrap"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.1")
    implementation("org.tavall.cloud:tavall-cloud-ai-broker-protocol") {
        version { branch = "main" }
    }
}
