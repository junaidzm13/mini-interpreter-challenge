import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    id("org.springframework.boot") version "2.6.6"
    id("org.jetbrains.kotlin.plugin.spring") version "1.6.20"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "com.csg.codeit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.12.8")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.getByName<Jar>("bootJar") {
    from("README.md") {
        into("BOOT-INF/classes/static")
    }
}
tasks.getByName<Jar>("jar") {
    enabled = false
}
