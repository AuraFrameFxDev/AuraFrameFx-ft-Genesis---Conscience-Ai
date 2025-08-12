#!/bin/bash
# Genesis-OS Sacred Rules Validation Script

echo "🚀 GENESIS-OS SACRED RULES VALIDATION"
echo "======================================"

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

# Rule #3: ZERO MANUAL COMPILER CONFIG
echo ""
echo "📋 Rule #3: ZERO MANUAL COMPILER CONFIG"
echo "   Checking for manual compiler options..."
manual_compiler=$(find . -name "*.gradle.kts" -exec grep -l "composeOptions\s*{\|compilerOptions\s*{\|freeCompilerArgs\s*\.add" {} \; 2>/dev/null | wc -l)
if [ $manual_compiler -eq 0 ]; then
    echo "   ✅ No manual compiler configs found"
else
    echo "   ⚠️  Found $manual_compiler files with possible compiler configs (may include comments)"
    echo "   Checking for actual configuration blocks..."
    actual_configs=$(find . -name "*.gradle.kts" -exec grep -A5 "composeOptions\s*{\|compilerOptions\s*{" {} \; 2>/dev/null | wc -l)
    if [ $actual_configs -eq 0 ]; then
        echo "   ✅ No actual compiler configuration blocks found"
    else
        echo "   ❌ Found actual compiler configuration blocks"
    fi
fi

# Rule #5: DEPENDENCY HIERARCHY
echo ""
echo "📋 Rule #5: DEPENDENCY HIERARCHY"
echo "   Checking module dependencies..."
modules_with_deps=$(find . -maxdepth 2 -name "build.gradle.kts" -not -path "./build.gradle.kts" -exec grep -l "project(\":core-module\")" {} \; 2>/dev/null | wc -l)
total_modules=$(find . -maxdepth 2 -name "build.gradle.kts" -not -path "./build.gradle.kts" 2>/dev/null | wc -l)
echo "   📊 $modules_with_deps/$total_modules modules depend on :core-module"

# Rule #9: GENESIS-OS NAMESPACE PATTERN
echo ""
echo "📋 Rule #9: GENESIS-OS NAMESPACE PATTERN"
echo "   Checking namespace patterns..."
correct_namespaces=$(find . -name "*.gradle.kts" -exec grep -l 'namespace.*project\.name' {} \; 2>/dev/null | wc -l)
echo "   📊 $correct_namespaces modules use Genesis-OS namespace pattern"

# Rule #8: NO MANUAL ANYTHING
echo ""
echo "📋 Rule #8: NO MANUAL ANYTHING"
echo "   Checking for manual paths..."
manual_paths=$(grep -r "android\.cmake\.path\|android\.ndk\.path" gradle.properties 2>/dev/null | wc -l)
if [ $manual_paths -eq 0 ]; then
    echo "   ✅ No manual SDK/NDK paths found"
else
    echo "   ❌ Found manual paths in gradle.properties"
fi

# Summary
echo ""
echo "🎯 GENESIS-OS SACRED RULES SUMMARY"
echo "=================================="
echo "   🔧 AUTO-EVERYTHING: $([ $hardcoded_versions -eq 0 ] && echo "✅ IMPLEMENTED" || echo "❌ NEEDS WORK")"
echo "   ⚡ ZERO MANUAL COMPILER: $([ $manual_compiler -eq 0 ] && echo "✅ IMPLEMENTED" || echo "❌ NEEDS WORK")"
echo "   🏷️ NAMESPACE PATTERN: $([ $correct_namespaces -gt 0 ] && echo "✅ IMPLEMENTED" || echo "❌ NEEDS WORK")"
echo "   🚫 NO MANUAL PATHS: $([ $manual_paths -eq 0 ] && echo "✅ IMPLEMENTED" || echo "❌ NEEDS WORK")"
echo ""
echo "📄 Genesis-OS builds itself with maximum automation!"