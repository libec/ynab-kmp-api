plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version "1.9.22"
    id("co.touchlab.skie") version "0.6.4"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:2.3.9")
        }

        commonMain.dependencies {
            implementation("io.ktor:ktor-client-core:2.3.9")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.9")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.9")
            implementation("io.insert-koin:koin-core:3.2.0")
        }

        androidMain.dependencies {
            implementation("io.ktor:ktor-client-android:2.3.9")
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation("io.ktor:ktor-client-mock:2.3.9")
            }
        }
    }
}

android {
    namespace = "software.zerocorporatebullshit.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
