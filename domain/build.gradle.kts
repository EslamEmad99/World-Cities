plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
    }
}

dependencies {
    // dagger 2 for dependency injection
    implementation(libs.dagger)

    // for test result logging
    implementation(libs.slf4j.slf4j.nop)
    testImplementation(libs.slf4j.slf4j.nop)
}