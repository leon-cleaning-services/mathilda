@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm {
        binaries {
            executable {
                mainClass.set("mathilda.cli.JvmMainKt")
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":lib"))
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
