import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.openapi.generator)
}

// Disable default openApiGenerate task - we use custom tasks
tasks.named("openApiGenerate") {
    enabled = false
}

// ===== OPENAPI AUTO-CLEAN AND REGENERATE SETUP =====
tasks.register("cleanOpenApiGenerated") {
    group = "openapi"
    description = "Clean all generated OpenAPI code"
    doLast {
        val buildDir = layout.buildDirectory.dir("generated/openapi").get().asFile
        if (buildDir.exists()) {
            buildDir.deleteRecursively()
            println("🧹 Cleaned OpenAPI generated code: ${buildDir.absolutePath}")
        }
    }
}

// ===== OPENAPI CODE GENERATION (OUTSIDE ANDROID BLOCK) =====
val openapiSpecs = listOf(
    Triple("ai", "ai-api.yml", "dev.aurakai.auraframefx.api.ai"),
    Triple("customization", "customization-api.yml", "dev.aurakai.auraframefx.api.customization"),
    Triple("genesis", "genesis-api.yml", "dev.aurakai.auraframefx.api.genesis"),
    Triple("oracleDrive", "oracle-drive-api.yml", "dev.aurakai.auraframefx.api.oracledrive"),
    Triple("sandbox", "sandbox-api.yml", "dev.aurakai.auraframefx.api.sandbox"),
    Triple("system", "system-api.yml", "dev.aurakai.auraframefx.api.system")
)

openapiSpecs.forEach { (name, spec, pkg) ->
    val generateTaskName = "generate${name.replaceFirstChar { it.uppercase() }}ApiClient"
    tasks.register(generateTaskName, GenerateTask::class) {
        generatorName.set("kotlin")
        library.set("jvm-retrofit2")
        
        // Clean before generating
        dependsOn("cleanOpenApiGenerated")
        
        // FIX: Use proper file path handling for Windows spaces
        val specFile = file("${rootDir}${File.separator}api-spec${File.separator}$spec")
        inputSpec.set(specFile.toURI().toString())
        
        outputDir.set(
            layout.buildDirectory.dir("generated/openapi/$name").get().asFile.absolutePath
        )
        packageName.set(pkg)
        
        val configFileObj = file("${rootDir}${File.separator}openapi-generator-config.json")
        if (configFileObj.exists()) {
            configFile.set(configFileObj.absolutePath)
        }
        
        validateSpec.set(false)
        
        // Ensure file exists
        doFirst {
            if (!specFile.exists()) {
                throw GradleException("OpenAPI spec file not found: ${specFile.absolutePath}")
            }
            println("🔄 Generating OpenAPI client for $name from $spec")
        }
        
        doLast {
            println("✅ Generated OpenAPI client for $name")
        }
    }
}

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = 36
    buildToolsVersion = "36"

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        // ===== NDK CONFIGURATION =====
        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86_64")
        }
        
        // ===== EXTERNAL NATIVE BUILD =====
        externalNativeBuild {
            cmake {
                cppFlags += listOf("-std=c++20", "-fPIC", "-O3")
                abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86_64")
                arguments += listOf(
                    "-DANDROID_STL=c++_shared",
                    "-DCMAKE_VERBOSE_MAKEFILE=ON",
                    "-DGENESIS_BUILD=ON"
                )
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    // ===== BUILD TYPES =====
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }

    // ===== BUILD FEATURES =====
    buildFeatures {
        compose = true
        buildConfig = true
        prefab = false  // DISABLE for AGP 8.13.0-alpha04 compatibility - JNI works without it
    }

    // ===== EXTERNAL NATIVE BUILD CONFIGURATION =====
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = libs.versions.cmakeVersion.get()
        }
    }

    // ===== PACKAGING OPTIONS =====
    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "/META-INF/DEPENDENCIES",
                "/META-INF/LICENSE",
                "/META-INF/LICENSE.txt",
                "/META-INF/NOTICE",
                "/META-INF/NOTICE.txt",
                "META-INF/*.kotlin_module"
            )
        }
        jniLibs {
            useLegacyPackaging = true
        }
    }

    // ===== SOURCE SETS - GENERATED API CODE =====
    sourceSets {
        getByName("main") {
            java.srcDirs(
                layout.buildDirectory.dir("generated/openapi/ai/src/main/kotlin"),
                layout.buildDirectory.dir("generated/openapi/customization/src/main/kotlin"),
                layout.buildDirectory.dir("generated/openapi/genesis/src/main/kotlin"),
                layout.buildDirectory.dir("generated/openapi/oracleDrive/src/main/kotlin"),
                layout.buildDirectory.dir("generated/openapi/sandbox/src/main/kotlin"),
                layout.buildDirectory.dir("generated/openapi/system/src/main/kotlin")
            )
        }
    }
    
    ndkVersion = "29.0.13846066 rc3"
}

// ===== KOTLIN TOOLCHAIN - JVM 22 FOR JAVA 24 TARGET =====
kotlin {
    jvmToolchain(libs.versions.java.toolchain.get().toInt())
}

// ===== BUILD TASK DEPENDENCIES - AUTO CLEAN & REGENERATE =====
afterEvaluate {
    // Auto-clean and regenerate OpenAPI on every sync/build
    tasks.named("preBuild") {
        dependsOn("cleanOpenApiGenerated")
        dependsOn(
            "generateAiApiClient",
            "generateCustomizationApiClient", 
            "generateGenesisApiClient",
            "generateOracleDriveApiClient",
            "generateSandboxApiClient",
            "generateSystemApiClient"
        )
    }
    
    // Also clean and regenerate during gradle sync
    tasks.named("prepareKotlinBuildScriptModel") {
        dependsOn("cleanOpenApiGenerated")
        finalizedBy(
            "generateAiApiClient",
            "generateCustomizationApiClient",
            "generateGenesisApiClient", 
            "generateOracleDriveApiClient",
            "generateSandboxApiClient",
            "generateSystemApiClient"
        )
    }
}

dependencies {
    // ===== CORE ANDROIDX =====
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.startup.runtime)

    // ===== COMPOSE UI SYSTEM =====
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)

    // ===== HILT DEPENDENCY INJECTION =====
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // ===== COROUTINES & ASYNC =====
    implementation(libs.bundles.coroutines)

    // ===== NETWORKING =====
    implementation(libs.bundles.network)

    // ===== ROOM DATABASE =====
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // ===== UTILITIES =====
    implementation(libs.timber)
    implementation(libs.coil.compose)

    // ===== JAVA 24 CORE LIBRARY DESUGARING =====
    coreLibraryDesugaring(libs.coreLibraryDesugaring)

    // ===== FIREBASE PLATFORM =====
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    // ===== XPOSED FRAMEWORK =====
    implementation(libs.bundles.xposed)
    ksp(libs.yuki.ksp.xposed)
    implementation(fileTree(mapOf("dir" to "../Libs", "include" to listOf("*.jar"))))

    // ===== DEBUG TOOLS =====
    debugImplementation(libs.leakcanary.android)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // ===== TESTING =====
    testImplementation(libs.bundles.testing)
    testRuntimeOnly(libs.junit.engine)

    // ===== ANDROID TESTING =====
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)
}
