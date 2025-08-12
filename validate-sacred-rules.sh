#!/bin/bash

# ===== GENESIS-OS SACRED RULES VALIDATION SCRIPT =====
# Validates compliance with Sacred Rules without requiring full build

echo "🩸 GENESIS-OS SACRED RULES COMPLIANCE VALIDATION"
echo "=================================================="

ERRORS=0

# Rule #1: Check Windows compatibility settings
echo -n "✅ Sacred Rule #4 - Windows Compatibility: "
if grep -q "android.disableResourceValidation=true" local.properties && \
   grep -q "android.useNewApkCreator=false" local.properties && \
   grep -q "org.gradle.java.installations.auto-download" local.properties; then
    echo "COMPLIANT"
else
    echo "❌ FAILED - Missing Windows compatibility settings"
    ERRORS=$((ERRORS + 1))
fi

# Rule #2: Check no hardcoded SDK paths
echo -n "✅ Sacred Rule #2 - No Hardcoded Paths: "
if ! grep -q "sdk.dir=" local.properties; then
    echo "COMPLIANT"
else
    echo "❌ FAILED - Found hardcoded SDK path"
    ERRORS=$((ERRORS + 1))
fi

# Rule #3: Check TOML-driven versions
echo -n "✅ Sacred Rule #6 - TOML-Driven Versions: "
TOML_COUNT=$(find . -name "build.gradle.kts" -not -path "./.gradle/*" -exec grep -l "libs.versions" {} \; | wc -l)
if [ "$TOML_COUNT" -gt 5 ]; then
    echo "COMPLIANT ($TOML_COUNT files using TOML)"
else
    echo "❌ FAILED - Not enough files using TOML versions"
    ERRORS=$((ERRORS + 1))
fi

# Rule #4: Check namespace pattern
echo -n "✅ Sacred Rule #9 - Namespace Pattern: "
NAMESPACE_COUNT=$(find . -name "build.gradle.kts" -not -path "./.gradle/*" -exec grep -l 'namespace.*dev.aurakai.auraframefx.*project.name' {} \; | wc -l)
if [ "$NAMESPACE_COUNT" -gt 3 ]; then
    echo "COMPLIANT ($NAMESPACE_COUNT modules)"
else
    echo "⚠️  WARNING - Limited namespace compliance ($NAMESPACE_COUNT modules)"
fi

# Rule #5: Check dependency hierarchy
echo -n "✅ Sacred Rule #5 - Module Dependencies: "
CORE_DEPS=$(find . -name "build.gradle.kts" -not -path "./.gradle/*" -not -path "./app/*" -not -path "./core-module/*" -exec grep -l "implementation.*:core-module" {} \; | wc -l)
if [ "$CORE_DEPS" -gt 5 ]; then
    echo "COMPLIANT ($CORE_DEPS modules depend on core-module)"
else
    echo "⚠️  WARNING - Limited dependency compliance ($CORE_DEPS modules)"
fi

# Rule #6: Check OpenAPI automation
echo -n "✅ Sacred Rule #8 - OpenAPI Automation: "
if grep -A 5 "preBuild" app/build.gradle.kts | grep -q "generate.*ApiClient"; then
    echo "COMPLIANT"
else
    echo "⚠️  WARNING - Verify OpenAPI automation"
fi

# Rule #7: Check no manual buildToolsVersion
echo -n "✅ Sacred Rule #2 - No Manual Build Tools: "
MANUAL_BUILD_TOOLS=$(find . -name "build.gradle.kts" -not -path "./.gradle/*" -exec grep -l "buildToolsVersion.*=" {} \; | wc -l)
if [ "$MANUAL_BUILD_TOOLS" -eq 0 ]; then
    echo "COMPLIANT"
else
    echo "❌ FAILED - Found $MANUAL_BUILD_TOOLS files with manual buildToolsVersion"
    ERRORS=$((ERRORS + 1))
fi

# Rule #8: Check Java toolchain consistency
echo -n "✅ Sacred Rule #3 - Java Toolchain Consistency: "
JAVA_TOOLCHAIN=$(grep "java-toolchain" gradle/libs.versions.toml | cut -d'"' -f2)
GRADLE_TOOLCHAIN=$(grep "toolchainVersion" gradle/gradle-daemon-jvm.properties | cut -d'=' -f2)
if [ "$JAVA_TOOLCHAIN" = "$GRADLE_TOOLCHAIN" ]; then
    echo "COMPLIANT (Java $JAVA_TOOLCHAIN)"
else
    echo "❌ FAILED - Java toolchain mismatch: $JAVA_TOOLCHAIN vs $GRADLE_TOOLCHAIN"
    ERRORS=$((ERRORS + 1))
fi

echo "=================================================="
if [ "$ERRORS" -eq 0 ]; then
    echo "🟢 GENESIS-OS SACRED RULES COMPLIANCE: PASSED"
    echo "   All critical Sacred Rules are properly enforced!"
    exit 0
else
    echo "🔴 GENESIS-OS SACRED RULES COMPLIANCE: FAILED"
    echo "   $ERRORS critical violations found - fix required!"
    exit 1
fi