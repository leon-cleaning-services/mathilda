plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.test.logger)
}

kotlin {
    explicitApi()

    jvm()
    androidTarget()
    linuxX64()
    linuxArm64()
    mingwX64()
    //iosX64()
    //iosArm64()
    //macosX64()
    //macosArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.collections.immutable)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

android {
    compileSdk = 35
    namespace = "mathilda"
}
