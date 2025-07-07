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
            implementation(libs.uri.kmp)
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
    compileSdk = 36
    namespace = "mathilda"
}

afterEvaluate {
    tasks.register<Copy>("copyRules") {
        from(project.projectDir)
        into("${project.projectDir}/src/jvmTest/resources")
        include("rules.json")
    }

    tasks.findByName("jvmTestProcessResources")?.dependsOn("copyRules")
}
