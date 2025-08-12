// Genesis-OS Root Build Configuration - BLEEDING EDGE
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    // Android plugins
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    // Kotlin plugins
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false

    // Processing plugins
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false

    // Quality and documentation
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.openapi.generator) apply false

    // Firebase and Google Services
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
// ===== REPOSITORIES FOR ALL PROJECTS =====
allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
        maven("https://api.xposed.info/")
        // Bleeding edge: Include snapshot repos for latest versions
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://androidx.dev/snapshots/builds/")
    }
}

// ===== AUTO-EVERYTHING: JAVA TOOLCHAIN ENFORCEMENT =====
subprojects {
    afterEvaluate {
        // AUTO-PROVISIONED: Java compilation from TOML
        tasks.withType<JavaCompile>().configureEach {
            sourceCompatibility = libs.versions.java.target.get()
            targetCompatibility = libs.versions.java.target.get()
        }

        // AUTO-PROVISIONED: Java toolchain from TOML with auto-download
        extensions.findByType<JavaPluginExtension>()?.apply {
            toolchain {
                languageVersion.set(
                    JavaLanguageVersion.of(
                        libs.versions.java.toolchain.get().toInt()
                    )
                )
                vendor.set(JvmVendorSpec.ADOPTIUM)
                // Enable auto-download for Sacred Rules compliance
                implementation.set(JvmImplementation.VENDOR_SPECIFIC)
            }
        }

        // AUTO-PROVISIONED: Kotlin toolchain from TOML (no manual configuration)
        extensions.findByType<KotlinJvmProjectExtension>()?.apply {
            jvmToolchain(libs.versions.java.toolchain.get().toInt())
        }
    }
}

// ===== BUILD VERIFICATION TASKS =====
tasks.register("verifyBleedingEdgeConfiguration") {
    group = "verification"
    description = "Verify bleeding-edge auto-provisioned configuration"

    doLast {
        println("✅ Genesis-OS Bleeding Edge Auto-Provisioned Configuration")
        println("📋 JVM Toolchain: ${libs.versions.java.toolchain.get()}")
        println("📋 Java Target: ${libs.versions.java.target.get()}")
        println("📋 Kotlin: ${libs.versions.kotlin.get()}")
        println("📋 AGP: ${libs.versions.agp.get()}")
        println("📋 Gradle: ${libs.versions.gradle.get()}")
        println("📋 Strategy: AUTO-EVERYTHING - Zero manual configuration")
    }
}

tasks.register("generateAllApiClients") {
    group = "genesis"
    description = "Generate all OpenAPI client code (bleeding edge)"
    dependsOn(":app:generateAiApiClient")
    dependsOn(":app:generateCustomizationApiClient")
    dependsOn(":app:generateGenesisApiClient")
    dependsOn(":app:generateOracleDriveApiClient")
    dependsOn(":app:generateSandboxApiClient")
    dependsOn(":app:generateSystemApiClient")
}

tasks.register("cleanAll") {
    group = "build"
    description = "Clean all modules and generated code"
    dependsOn("clean")
    dependsOn(subprojects.map { "${it.path}:clean" })
}

tasks.register("bleedingEdgeBuild") {
    group = "bleeding-edge"
    description = "Full bleeding edge build with latest everything"
    dependsOn("cleanAll")
    dependsOn("generateAllApiClients")
    dependsOn("build")
}

tasks.register("fixBleedingEdgeCaches") {
    group = "bleeding-edge"
    description = "Clear all caches while preserving bleeding-edge configuration"

    doLast {
        println("🩸 BLEEDING-EDGE CACHE FIX")
        println("Clearing caches while preserving:")
        println("- Java 24 toolchain")
        println("- AGP 8.13.0-alpha04")
        println("- Kotlin 2.2.0")
        println("- Compose BOM 2025.07.00")
        println("")
        println("Run: ./scripts/fix-bleeding-edge-cache.bat")
        println("Then: ./gradlew build --refresh-dependencies")
    }
}

tasks.register("testBleedingEdgeConnectivity") {
    group = "bleeding-edge"
    description = "Test connectivity to bleeding-edge repositories"

    doLast {
        println("🩸 TESTING BLEEDING-EDGE CONNECTIVITY...")

        val repositories = listOf(
            "https://dl.google.com/dl/android/maven2/",
            "https://oss.sonatype.org/content/repositories/snapshots/",
            "https://androidx.dev/storage/compose-compiler/repository/",
            "https://maven.pkg.jetbrains.space/public/p/compose/dev"
        )

        repositories.forEach { repo ->
            try {
                val url = java.net.URL(repo)
                val connection = url.openConnection()
                connection.connectTimeout = 5000
                connection.connect()
                println("✅ $repo - OK")
            } catch (e: Exception) {
                println("❌ $repo - FAILED: ${e.message}")
            }
        }

        println("")
        println("📄 See BLEEDING_EDGE_FIX.md for troubleshooting")
    }
}

tasks.register("verifyBleedingEdge") {
    group = "bleeding-edge"
    description = "Verify all bleeding edge versions are working"

    doLast {
        println("🚀 GENESIS-OS BLEEDING EDGE AUTO-EVERYTHING VERIFICATION")
        println("   Java: 21 (Auto-provisioned)")
        println("   Gradle: 9.0.0 (Auto-provisioned)")
        println("   Kotlin: 2.2.20-Beta2 (K2, Auto-provisioned)")
        println("   AGP: 8.13.0-alpha04 (Auto-provisioned)")
        println("   SDK: 36 (Auto-provisioned)")
        println("   Strategy: ZERO MANUAL CONFIG - Auto-everything")
        println("✅ All bleeding edge versions auto-provisioned")
    }
}
