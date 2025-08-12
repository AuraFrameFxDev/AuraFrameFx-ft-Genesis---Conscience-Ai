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

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        // AUTO-EVERYTHING: Use libs.versions.toml for Java version
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.target.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.target.get())
        isCoreLibraryDesugaringEnabled = true
    }

    packaging {
        resources.excludes += setOf(
            "/META-INF/{AL2.0,LGPL2.1}",
            "/META-INF/AL2.0",
            "/META-INF/LGPL2.1"
        )
    }

    sourceSets {
        getByName("main") {
            kotlin.srcDir("build/generated/openapi/src/main/kotlin")
        }
    }
    // AUTO-PROVISIONED: Remove hardcoded buildToolsVersion
    buildToolsVersion = libs.versions.compileSdk.get()
}

// ===== ZERO MANUAL COMPILER CONFIG: AUTO-PROVISIONED KOTLIN =====
kotlin {
    jvmToolchain(libs.versions.java.toolchain.get().toInt())
    
    // SACRED RULE #3: K2 compiler handles everything automatically
    // NO manual compilerOptions - K2 auto-provisions everything
}

dependencies {
    // Project modules
    implementation(project(":core-module"))

    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose - Genesis UI System
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Coroutines - Genesis Async Processing  
    implementation(libs.bundles.coroutines)

    // Kotlin reflection for KSP (disabled for now)
    implementation(libs.kotlin.reflect)

    // OpenAPI Generated Code Dependencies
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)

    // Core library desugaring
    coreLibraryDesugaring(libs.coreLibraryDesugaring)

    // Testing
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // System interaction and documentation (using local JAR files)
    implementation(files("${project.rootDir}/Libs/api-82.jar"))
    implementation(files("${project.rootDir}/Libs/api-82-sources.jar"))
}