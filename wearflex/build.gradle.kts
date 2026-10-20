plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
    `maven-publish`
}

kotlin {
    // 1. Target Platforms
    androidTarget {
        publishLibraryVariants("release", "debug")
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
        }

        androidMain.dependencies {
            implementation("androidx.core:core-ktx:1.12.0")
        }

        named("desktopMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }

    // 2. JitPack-Compatible Publishing Configuration
    publishing {
        publications {
            withType<MavenPublication> {
                groupId = "com.github.I-am-Manu-Singh" // <--- Updated for JitPack
                artifactId = "wearflex"
                version = "1.0.0"
            }
        }
    }
}

android {
    namespace = "com.creativeblisslabs.wearflex"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}