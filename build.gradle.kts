plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.test.logger) apply false
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}
