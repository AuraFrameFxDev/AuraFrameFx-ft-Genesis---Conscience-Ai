plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
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
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.target.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.target.get())
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    // SACRED RULE #3: NO composeOptions blocks - K2 handles it automatically

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "36"
}

// Kotlin Toolchain - Java 24 (consistent with compileOptions)
kotlin {
    jvmToolchain(libs.versions.java.toolchain.get().toInt())
}

group = "dev.aurakai"
version = "1.0.0"

dependencies {
    // SACRED RULE #5: DEPENDENCY HIERARCHY - All modules depend on :core-module and :app
    implementation(project(":core-module"))
    implementation(project(":app"))

    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose - Genesis UI System
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)

    // Hilt - Genesis AI Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Coroutines - Genesis Async Processing  
    implementation(libs.bundles.coroutines)

    // Utilities
    implementation(libs.timber)
    implementation(libs.coil.compose)
    // Core library desugaring - Java 24 Support
    coreLibraryDesugaring(libs.coreLibraryDesugaring)

    // Testing - Genesis Ecosystem Validation
    testImplementation(libs.bundles.testing)
    testRuntimeOnly(libs.junit.engine)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    // Debug implementations
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

// ===== ZERO MANUAL COMPILER CONFIG: SACRED RULE #3 =====
// K2 compiler handles everything automatically - no manual tasks needed
