plugins {
    id("java")
    id("application")
}

group = "io.github.jeniths006"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
    implementation("net.java.dev.jna:jna:5.17.0")
    implementation("net.java.dev.jna:jna-platform:5.17.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("io.github.jeniths006.runtimeguard.cli.RuntimeGuardApplication")
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "io.github.jeniths006.runtimeguard.cli.RuntimeGuardApplication")
    }

    from(sourceSets["main"].output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.isFile }.map { zipTree(it) }
    })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

