import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    val kotlinVersion = "1.3.61"
    val springBootVersion = "2.2.4.RELEASE"
    idea
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version "1.0.8.RELEASE" apply false
    java
}

allprojects {
    group = "nl.kadaster.land_administration"
    version = "0.1.0-SNAPSHOT" // File("VERSION").useLines { it.toList() }.first()

    repositories {
        mavenCentral()
        jcenter()
    }
}

configure(subprojects) {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.gradle.java")

    dependencies {
        val arrowVersion = "0.9.0"
        val axonFrameworkVersion = "4.2.1"

        "implementation"(kotlin("stdlib"))      // version comes from applied kotlin plugin
        "implementation"(kotlin("reflect"))     // version comes from applied kotlin plugin
        "implementation"(group = "io.arrow-kt", name = "arrow-core-data", version = arrowVersion)

        "implementation"("org.axonframework:axon-spring-boot-starter:$axonFrameworkVersion")

        "implementation"("org.springframework.boot:spring-boot-starter-data-jpa")
        "implementation"("org.springframework.boot:spring-boot-starter-mustache")
        "implementation"("org.springframework.boot:spring-boot-starter-web")
        "implementation"("com.fasterxml.jackson.module:jackson-module-kotlin")
        "implementation"("org.jetbrains.kotlin:kotlin-reflect")
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        "runtimeOnly"("com.h2database:h2")

        "testImplementation"("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "junit")
        }
        "testImplementation"("io.kotlintest:kotlintest-runner-junit5:3.3.2")
        "testImplementation"("io.kotlintest:kotlintest-assertions-arrow:3.3.2")
        "testImplementation"("org.mockito:mockito-all:1.10.19")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    tasks.withType<Jar> {
        enabled = true
    }
    tasks.getByName<BootJar>("bootJar") {
        enabled = false
    }
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
    testLogging.showStandardStreams = true
}

idea.module {
    isDownloadJavadoc = true
    isDownloadSources = true
    targetBytecodeVersion = JavaVersion.VERSION_1_8
}

val cl = Action<Task> { println("I'm ${this.project.name}") }

tasks.register("hello") { doLast(cl) }

project(":events") {
    tasks.register("hello") { doLast(cl) }
}
