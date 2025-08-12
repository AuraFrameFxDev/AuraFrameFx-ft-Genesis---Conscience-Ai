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

// ===== OPENAPI CODE GENERATION WITH CLEAN REGENERATION =====
val openapiSpecs = listOf(
    Triple("ai", "ai-api.yml", "dev.aurakai.auraframefx.api.ai"),
    Triple("customization", "customization-api.yml", "dev.aurakai.auraframefx.api.customization"),
    Triple("genesis", "genesis-api.yml", "dev.aurakai.auraframefx.api.genesis"),
    Triple("oracleDrive", "oracle-drive-api.yml", "dev.aurakai.auraframefx.api.oracledrive"),
    Triple("sandbox", "sandbox-api.yml", "dev.aurakai.auraframefx.api.sandbox"),
    Triple("system", "system-api.yml", "dev.aurakai.auraframefx.api.system")
)

// Task to clean all OpenAPI generated directories
tasks.register("cleanOpenApiGenerated") {
    group = "openapi"
    description = "Clean all OpenAPI generated directories"
    
    doLast {
        val generatedDir = layout.buildDirectory.dir("generated/openapi").get().asFile
        if (generatedDir.exists()) {
            println("Cleaning OpenAPI generated directory: ${generatedDir.absolutePath}")
            generatedDir.deleteRecursively()
        }
    }
}

// Generate individual API client tasks with clean regeneration
openapiSpecs.forEach { (name, spec, pkg) ->
    val taskName = "generate${name.replaceFirstChar { it.uppercase() }}ApiClient"
    val cleanTaskName = "clean${name.replaceFirstChar { it.uppercase() }}ApiClient"
    
    // Individual clean task for each API
    tasks.register(cleanTaskName) {
        group = "openapi"
        description = "Clean generated code for $name API"
        
        doLast {
            val outputDir = layout.buildDirectory.dir("generated/openapi/$name").get().asFile
            if (outputDir.exists()) {
                println("Cleaning $name API generated directory: ${outputDir.absolutePath}")
                outputDir.deleteRecursively()
            }
        }
    }
    
    // Generation task
    tasks.register(taskName, GenerateTask::class) {
        group = "openapi"
        description = "Generate API client for $name"
        
        // Always clean before generating
        dependsOn(cleanTaskName)
        
        generatorName.set("kotlin")
        library.set("jvm-retrofit2")
        
        // Use proper file path handling with URI conversion for Windows compatibility
        val specFile = file("$rootDir/api-spec/$spec")
        inputSpec.set(specFile.toURI().toString())  // Convert to URI string for Windows compatibility
        
        val outputDirFile = layout.buildDirectory.dir("generated/openapi/$name").get().asFile
        outputDir.set(outputDirFile.absolutePath)
        packageName.set(pkg)
        
        val configFileObj = file("$rootDir/openapi-generator-config.json")
        if (configFileObj.exists()) {
            configFile.set(configFileObj.absolutePath)
        }
        
        validateSpec.set(false)
        
        // Ensure clean directory and file exists
        doFirst {
            if (!specFile.exists()) {
                throw GradleException("OpenAPI spec file not found: ${specFile.absolutePath}")
            }
            
            // Ensure output directory is clean
            if (outputDirFile.exists()) {
                outputDirFile.deleteRecursively()
            }
            outputDirFile.mkdirs()
            
            println("Generating $name API client from ${specFile.name}")
        }
        
        // Always consider this task out of date to force regeneration
        outputs.upToDateWhen { false }
    }
}

// Master task to generate all API clients
tasks.register("generateAllApiClients") {
    group = "openapi"
    description = "Generate all API clients with clean regeneration"
    
    dependsOn("cleanOpenApiGenerated")
    dependsOn(openapiSpecs.map { (name, _, _) -> 
        "generate${name.replaceFirstChar { it.uppercase() }}ApiClient" 
    })
    
    // Task ordering is configured in afterEvaluate block
}

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = 36
    buildToolsVersion = "36.0.0"

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        minSdk = 33
        targetSdkPreview = "CANARY"
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
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
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
    ndkVersion = libs.versions.ndkVersion.get()

    // Auto-provisioned NDK
}

// ===== KOTLIN TOOLCHAIN - AUTO-PROVISIONED JAVA 24 =====
kotlin {
    jvmToolchain(libs.versions.java.toolchain.get().toInt())
}

// ===== BUILD TASK DEPENDENCIES WITH CLEAN GENERATION =====
afterEvaluate {
    // Clean and generate on every sync/build
    tasks.named("preBuild") {
        dependsOn("generateAllApiClients")
    }
    
    // Ensure generation runs on every important build lifecycle event
    tasks.matching { 
        it.name.startsWith("assemble") || 
        it.name.startsWith("compile") ||
        it.name == "prepareKotlinBuildScriptModel" ||
        it.name == "generateDebugSources" ||
        it.name == "generateReleaseSources"
    }.configureEach {
        dependsOn("generateAllApiClients")
    }
    
    // Hook into source generation tasks
    tasks.withType<JavaCompile> {
        dependsOn("generateAllApiClients")
    }
    
    // Ensure clean runs before any compile tasks
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        dependsOn("generateAllApiClients")
    }
    
    // Configure task ordering AFTER all tasks are created
    openapiSpecs.forEach { (name, _, _) ->
        tasks.named("generate${name.replaceFirstChar { it.uppercase() }}ApiClient") {
            mustRunAfter("cleanOpenApiGenerated")
        }
    }
}

dependencies {
    // ===== CORE ANDROIDX =====
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

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
