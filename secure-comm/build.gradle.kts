plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
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
    }

    testOptions {
        targetSdk = libs.versions.targetSdk.get().toInt()
    }

    lint {
        targetSdk = libs.versions.targetSdk.get().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    // SACRED RULE #5: DEPENDENCY HIERARCHY - All modules depend on :core-module and :app
    implementation(project(":core-module"))
    implementation(project(":app"))

    // Kotlin
    implementation(libs.kotlin.reflect)

    // AndroidX
    implementation(libs.androidx.core.ktx)

    // Security
    implementation(libs.androidxSecurity)
    implementation(libs.tink)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Core library desugaring
    coreLibraryDesugaring(libs.coreLibraryDesugaring)

    // Testing
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Bouncy Castle for cryptographic operations
    implementation(libs.bouncycastle)

    // System interaction and documentation (using local JAR files)
    implementation(files("${project.rootDir}/Libs/api-82.jar"))
    implementation(files("${project.rootDir}/Libs/api-82-sources.jar"))
}