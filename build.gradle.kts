import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    kotlin("jvm") version "1.3.61"
    java
}

group = "nl.kadaster.land_administration"
version = "0.1.0-SNAPSHOT" // File("VERSION").useLines { it.toList() }.first()


allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
}

dependencies {
    implementation(kotlin("stdlib"))      // version comes from applied kotlin plugin
    implementation(kotlin("reflect"))     // version comes from applied kotlin plugin
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
//    kotlinOptions.allWarningsAsErrors = true
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
    testLogging.showStandardStreams = true
}

//application {
//    mainClassName = "apps.LandAdministrationServer"
//}

idea.module {
    isDownloadJavadoc = true
    isDownloadSources = true
    targetBytecodeVersion = JavaVersion.VERSION_1_8
}

