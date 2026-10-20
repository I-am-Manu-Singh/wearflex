plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
    `maven-publish`
}

kotlin {
    // Apply default hierarchy template so iosMain is automatically shared across all iOS targets
    applyDefaultHierarchyTemplate()

    // 1. Android Target
    androidTarget {
        publishLibraryVariants("release", "debug")
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    // 2. Desktop JVM Target
    jvm("desktop")

    // 3. iOS Targets (prevents missing variant errors in full KMP projects)
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
        }

        androidMain.dependencies {
            implementation("androidx.core:core-ktx:1.13.1")
        }

        named("desktopMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }

    // 4. JitPack Publishing Configuration (Bumped to 1.1.0)
    publishing {
        publications {
            withType<MavenPublication> {
                groupId = "com.github.I-am-Manu-Singh"
                artifactId = "wearflex"
                version = "1.1.0"
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