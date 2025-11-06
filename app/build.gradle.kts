import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    application
    id("com.github.ben-manes.versions") version "0.52.0"
    id("checkstyle")
    id("org.sonarqube") version "6.2.0.5505"
    id("jacoco")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.freefair.lombok") version "9.0.0"
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.7.0")
    implementation("org.slf4j:slf4j-simple:2.0.17")
    implementation("io.javalin:javalin-rendering:6.7.0")
    implementation("gg.jte:jte:3.2.1")
    implementation("com.h2database:h2:2.4.240")
    implementation("com.zaxxer:HikariCP:7.0.2")
    implementation("org.postgresql:postgresql:42.7.6")
    implementation("org.jsoup:jsoup:1.21.2")
    implementation("com.konghq:unirest-java:3.14.5")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.javalin:javalin-testtools:6.6.0")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
}

checkstyle {
    toolVersion = "10.17.0"
    configFile = rootProject.file("config/checkstyle/checkstyle.xml")
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        showStandardStreams = true
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
}

sonar {
    properties {
        property("sonar.projectKey", "nastya-ermolaeva_java-project-72")
        property("sonar.organization", "nastya-ermolaeva")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "${buildDir}/reports/jacoco/test/jacocoTestReport.xml")
    }
}