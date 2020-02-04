import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    application
}

dependencies {
    implementation(project(":ws"))
    implementation("io.swagger:swagger-annotations:1.6.0")
}

tasks.getByName<BootJar>("bootJar") {
    classifier = "boot"
}

application {
    mainClassName = "nl.kadaster.land_administration.LandAdministrationApplication"
}