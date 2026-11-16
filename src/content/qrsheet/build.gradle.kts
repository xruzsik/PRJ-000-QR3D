plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

dependencies {
    implementation(libs.zxing.core)
}

application {
    mainClass.set("com.smvit.ar.qrsheet.MainKt")
}

// Resolve relative output paths against the repository root.
tasks.named<JavaExec>("run") {
    workingDir = rootDir
}
