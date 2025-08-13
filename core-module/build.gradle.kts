// Genesis Protocol - Clean Core Library Module (No Quality Tools Forced)
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    // SACRED RULE #9: Genesis-OS namespace pattern
    namespace = "dev.aurakai.auraframefx.${project.name}"
    // AUTO-EVERYTHING: Use libs.versions.toml
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    // AUTO-PROVISIONED: Compile options handled by root build.gradle.kts

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}", "/META-INF/AL2.0", "/META-INF/LGPL2.1"
            )
        }
    }
    // AUTO-PROVISIONED: buildToolsVersion removed - let Gradle auto-provision per Sacred Rules
}

// ===== AUTO-PROVISIONED: Kotlin toolchain handled by root build.gradle.kts =====

dependencies {
    // Core AndroidX bundle  
    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.activity.compose)

    // Compose bundle
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.animated.vector.drawable)
    implementation(libs.androidx.material)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Networking bundle (for shared API models)
    implementation(libs.bundles.networking)

    // Core library desugaring
    coreLibraryDesugaring(libs.coreLibraryDesugaring)

    // Testing bundle
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Xposed Framework (for core hooks)
    implementation(
        fileTree(
            mapOf(
                "dir" to "${project.rootDir}/Libs",
                "include" to listOf("*.jar")
            )
        )
    )
}
