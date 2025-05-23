dependencies {
    api(project(":server:libs:config:jackson-config"))

    implementation(rootProject.libs.loki.logback.appender)
    implementation("org.springframework:spring-test")
    implementation("org.springframework.boot:spring-boot-test")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.data:spring-data-jdbc")
    implementation("org.testcontainers:testcontainers")
    implementation("org.testcontainers:postgresql")
    implementation("org.springframework.boot:spring-boot-testcontainers")

    runtimeOnly("com.zaxxer:HikariCP")
    runtimeOnly("org.postgresql:postgresql")

    runtimeOnly("org.apache.activemq:artemis-jakarta-server")
    runtimeOnly("org.springframework.boot:spring-boot-starter-artemis")
    runtimeOnly(project(":server:libs:core:message:message-broker:message-broker-jms"))
}
