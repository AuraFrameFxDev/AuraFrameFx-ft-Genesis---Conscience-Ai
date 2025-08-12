#!/bin/bash

# Auto Provisioning Manual Verification Script
# This script helps verify that the auto-provisioning feature is correctly implemented

echo "🔍 Auto Provisioning Implementation Verification"
echo "================================================"

# Check if all required files exist
echo "📁 Checking file structure..."

files=(
    "app/src/main/java/dev/aurakai/auraframefx/services/AutoProvisioningService.kt"
    "app/src/main/java/dev/aurakai/auraframefx/di/AutoProvisioningModule.kt"
    "app/src/main/java/dev/aurakai/auraframefx/initializers/AppInitializerInitializer.kt"
    "app/src/test/java/dev/aurakai/auraframefx/services/AutoProvisioningServiceTest.kt"
    "AUTO_PROVISIONING.md"
)

for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file exists"
    else
        echo "❌ $file missing"
    fi
done

# Check AndroidManifest.xml for startup provider
echo ""
echo "📱 Checking AndroidManifest.xml configuration..."
if grep -q "androidx.startup.InitializationProvider" app/src/main/AndroidManifest.xml; then
    echo "✅ Startup provider configured"
else
    echo "❌ Startup provider missing"
fi

if grep -q "AppInitializerInitializer" app/src/main/AndroidManifest.xml; then
    echo "✅ Auto-provisioning initializer registered"
else
    echo "❌ Auto-provisioning initializer not registered"
fi

# Check build.gradle.kts for dependency
echo ""
echo "🏗️ Checking build configuration..."
if grep -q "androidx.startup.runtime" app/build.gradle.kts; then
    echo "✅ androidx-startup dependency added"
else
    echo "❌ androidx-startup dependency missing"
fi

# Check version catalog
echo ""
echo "📚 Checking version catalog..."
if grep -q "startup.*=" gradle/libs.versions.toml; then
    echo "✅ Startup version defined"
else
    echo "❌ Startup version missing"
fi

if grep -q "androidx-startup-runtime" gradle/libs.versions.toml; then
    echo "✅ Startup library defined"
else
    echo "❌ Startup library missing"
fi

# Check key implementation details
echo ""
echo "🔧 Checking implementation details..."

# Check AutoProvisioningService for key features
if grep -q "@Singleton" app/src/main/java/dev/aurakai/auraframefx/services/AutoProvisioningService.kt; then
    echo "✅ AutoProvisioningService is singleton"
else
    echo "❌ AutoProvisioningService singleton annotation missing"
fi

if grep -q "performAutoProvisioning" app/src/main/java/dev/aurakai/auraframefx/services/AutoProvisioningService.kt; then
    echo "✅ Core provisioning method exists"
else
    echo "❌ Core provisioning method missing"
fi

if grep -q "dataStore.edit" app/src/main/java/dev/aurakai/auraframefx/services/AutoProvisioningService.kt; then
    echo "✅ State persistence implemented"
else
    echo "❌ State persistence missing"
fi

# Check AppInitializerInitializer integration
if grep -q "AutoProvisioningService" app/src/main/java/dev/aurakai/auraframefx/initializers/AppInitializerInitializer.kt; then
    echo "✅ Initializer integrates with auto-provisioning"
else
    echo "❌ Initializer missing auto-provisioning integration"
fi

if grep -q "EntryPointAccessors" app/src/main/java/dev/aurakai/auraframefx/initializers/AppInitializerInitializer.kt; then
    echo "✅ Hilt entry point access configured"
else
    echo "❌ Hilt entry point access missing"
fi

# Summary
echo ""
echo "📋 Verification Summary"
echo "======================"
echo "This verification checks that the auto-provisioning feature has been"
echo "correctly implemented with all necessary components in place."
echo ""
echo "If all checks pass (✅), the auto-provisioning feature should work"
echo "correctly when the app is built and deployed."
echo ""
echo "If any checks fail (❌), review the implementation and ensure all"
echo "components are properly configured."