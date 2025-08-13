#!/bin/bash
# Enhanced Genesis-OS Sacred Rules Validation Script
# Now validates Java 25 bleeding edge compliance

echo "🚀 GENESIS-OS SACRED RULES VALIDATION (Java 25 Bleeding Edge)"
echo "=============================================================="

# Rule #1: AUTO-EVERYTHING PRINCIPLE  
echo ""
echo "📋 Rule #1: AUTO-EVERYTHING PRINCIPLE"
echo "   Checking for hardcoded versions..."
hardcoded_versions=$(find . -name "*.gradle.kts" -exec grep -l "VERSION_[0-9]\|jvmToolchain([0-9]\|compileSdk = [0-9]" {} \; 2>/dev/null | wc -l)
if [ $hardcoded_versions -eq 0 ]; then
    echo "   ✅ No hardcoded versions found"
else
    echo "   ❌ Found $hardcoded_versions files with hardcoded versions"
    find . -name "*.gradle.kts" -exec grep -l "VERSION_[0-9]\|jvmToolchain([0-9]\|compileSdk = [0-9]" {} \; 2>/dev/null
fi

# Rule #2: JAVA 25 BLEEDING EDGE COMPLIANCE
echo ""
echo "📋 Rule #2: JAVA 25 BLEEDING EDGE COMPLIANCE"
java_toolchain_version=$(grep "java-toolchain" gradle/libs.versions.toml | grep -o '"[0-9]*"' | tr -d '"')
echo "   Java Toolchain Version: $java_toolchain_version"
if [ "$java_toolchain_version" = "25" ]; then
    echo "   ✅ Java 25 toolchain configured correctly"
else
    echo "   ❌ Expected Java 25, found Java $java_toolchain_version"
fi

# Rule #3: ZERO MANUAL COMPILER CONFIG
echo ""
echo "📋 Rule #3: ZERO MANUAL COMPILER CONFIG"
echo "   Checking for manual compiler options..."
manual_compiler=$(find . -name "*.gradle.kts" -exec grep -l "compileOptions\s*{" {} \; 2>/dev/null | wc -l)
if [ $manual_compiler -eq 0 ]; then
    echo "   ✅ No manual compiler configs found"
else
    echo "   ⚠️  Found $manual_compiler files with compiler configs"
    find . -name "*.gradle.kts" -exec grep -l "compileOptions\s*{" {} \; 2>/dev/null | head -3
fi

# Rule #4: BUNDLE OPTIMIZATION
echo ""
echo "📋 Rule #4: BUNDLE OPTIMIZATION"
echo "   Checking for dependency bundles..."
bundles_count=$(grep -A 20 "\[bundles\]" gradle/libs.versions.toml | grep -c "=" 2>/dev/null || echo "0")
echo "   Bundle definitions found: $bundles_count"
if [ $bundles_count -gt 0 ]; then
    echo "   ✅ Dependency bundles configured"
    grep -A 10 "\[bundles\]" gradle/libs.versions.toml | grep "=" | head -3
else
    echo "   ❌ No dependency bundles found"
fi

# Rule #5: OPENAPI 7.14.0 COMPLIANCE
echo ""
echo "📋 Rule #5: OPENAPI 7.14.0 COMPLIANCE"
openapi_version=$(grep "openApiGenerator" gradle/libs.versions.toml | grep -o '"[0-9.]*"' | tr -d '"')
echo "   OpenAPI Generator Version: $openapi_version"
if [ "$openapi_version" = "7.14.0" ]; then
    echo "   ✅ OpenAPI 7.14.0 configured correctly"
else
    echo "   ❌ Expected 7.14.0, found $openapi_version"
fi

# Rule #6: BLEEDING EDGE REPOSITORY ACCESS
echo ""
echo "📋 Rule #6: BLEEDING EDGE REPOSITORY ACCESS"
echo "   Checking repository configuration..."
google_repos=$(grep -c "google()" settings.gradle.kts 2>/dev/null || echo "0")
if [ $google_repos -gt 0 ]; then
    echo "   ✅ Google repositories configured"
else
    echo "   ❌ Google repositories not found"
fi

# Rule #7: GRADLE 9.0.0 COMPLIANCE
echo ""
echo "📋 Rule #7: GRADLE 9.0.0 COMPLIANCE"
gradle_version=$(grep "gradle-9" gradle/wrapper/gradle-wrapper.properties | grep -o "9\.[0-9.]*" | head -1)
echo "   Gradle Version: $gradle_version"
if [ "$gradle_version" = "9.0.0" ]; then
    echo "   ✅ Gradle 9.0.0 configured correctly"
else
    echo "   ❌ Expected 9.0.0, found $gradle_version"
fi

echo ""
echo "🎯 BLEEDING EDGE SUMMARY"
echo "========================"
echo "   Java Toolchain: $java_toolchain_version (Target: 25)"
echo "   OpenAPI: $openapi_version (Target: 7.14.0)"
echo "   Gradle: $gradle_version (Target: 9.0.0)"
echo "   Manual Configs: $manual_compiler (Target: 0)"
echo "   Bundles: $bundles_count (Target: >0)"

if [ "$java_toolchain_version" = "25" ] && [ "$openapi_version" = "7.14.0" ] && [ "$gradle_version" = "9.0.0" ] && [ "$manual_compiler" -eq 0 ] && [ "$bundles_count" -gt 0 ]; then
    echo ""
    echo "🎉 ALL SACRED RULES COMPLIANT - BLEEDING EDGE PERFECTION!"
else
    echo ""
    echo "⚠️  Some rules need attention for full bleeding edge compliance"
fi