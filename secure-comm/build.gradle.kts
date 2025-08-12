import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "dev.aurakai.auraframefx.securecomm"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    // Required for Hilt testing
    hilt {
        enableAggregatingTask = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
        isCoreLibraryDesugaringEnabled = true
    }

    packaging {
        resources.excludes += setOf(
            "/META-INF/{AL2.0,LGPL2.1}", "/META-INF/AL2.0", "/META-INF/LGPL2.1"
        )
    }

    sourceSets {
        getByName("main") {
            kotlin.srcDir("build/generated/openapi/src/main/kotlin")
            kotlin.srcDir("build/generated/ksp/main/kotlin")
        }
    }

    compileSdk = 36

    kotlin {
        jvmToolchain(23)
        compilerOptions {
            jvmTarget = JvmTarget.JVM_23
            freeCompilerArgs.addAll(
                "-Xskip-prerelease-check",
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlin.ExperimentalStdlibApi",
                "-Xjvm-default=all"
            )
        }
    }
    dependencies {
        // Project modules
        implementation(project(":core-module"))

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

        // JUnit 4 for Android tests
        testImplementation(libs.junit)

        // Kotlin Coroutines Test
        testImplementation(libs.kotlinx.coroutines.test)

        // MockK
        testImplementation(libs.mockk)

        // Turbine for Flow testing
        testImplementation(libs.turbine)

        // AndroidX Test - Core
        androidTestImplementation(libs.androidx.core)

        // AndroidX JUnit
        androidTestImplementation(libs.androidx.test.ext.junit)

        // Espresso
        androidTestImplementation(libs.espresso.core)

        // Hilt Testing
        androidTestImplementation(libs.hilt.android.testing)
        kspAndroidTest(libs.hilt.compiler)

        // Architecture Components Testing
        androidTestImplementation(libs.androidx.core.testing)

        // WorkManager Testing
        androidTestImplementation(libs.androidx.work.testing)

        // Bouncy Castle for cryptographic operations
        implementation(libs.bouncycastle)

        // System interaction and documentation (using local JAR files)
        implementation(files("${project.rootDir}/Libs/api-82.jar"))
        implementation(files("${project.rootDir}/Libs/api-82-sources.jar"))
    }
}
