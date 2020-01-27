import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("plugin.jpa") version "1.3.61"
    id("org.springframework.boot") version "2.2.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm")
    kotlin("plugin.spring") version "1.3.61"
}

val arrowVersion = "0.9.0"
val springBootVersion = "2.2.4.RELEASE"
val axonFrameworkVersion = "4.2.1"

dependencies {
    implementation(kotlin("stdlib"))      // version comes from applied kotlin plugin
    implementation(kotlin("reflect"))     // version comes from applied kotlin plugin
    implementation(group = "io.arrow-kt", name = "arrow-core-data", version = arrowVersion)

//    implementation("org.axonframework:axon-spring:$axonFrameworkVersion")
    implementation("org.axonframework:axon-spring-boot-starter:$axonFrameworkVersion")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("com.h2database:h2")
//    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testImplementation("io.kotlintest:kotlintest-assertions-arrow:3.3.2")
    testImplementation("org.mockito:mockito-all:1.10.19")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
//task testsJar(type: Jar) {
//    archiveClassifier = "tests"
//    from(sourceSets.test.output)
//}
//
//publishing.publications.maven.artifact(testsJar)
