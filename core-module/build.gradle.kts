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

    compileOptions {
        // AUTO-EVERYTHING: Use libs.versions.toml for Java version
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.target.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.target.get())
        isCoreLibraryDesugaringEnabled = true
    }

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

// ===== ZERO MANUAL COMPILER CONFIG: AUTO-PROVISIONED KOTLIN =====
kotlin {
    jvmToolchain(libs.versions.java.toolchain.get().toInt())
    
    // SACRED RULE #3: K2 compiler handles everything automatically
    // NO manual compilerOptions - K2 auto-provisions everything
}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.animated.vector.drawable)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Networking (for shared API models)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)

    // Core library desugaring
    coreLibraryDesugaring(libs.coreLibraryDesugaring)

    // Testing
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
