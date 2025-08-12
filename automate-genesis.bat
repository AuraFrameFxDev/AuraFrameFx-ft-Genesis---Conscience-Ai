@echo off
:: ===== GENESIS-OS DESKTOP AUTOMATION SCRIPT =====
:: "If it can be automated, it MUST be automated!"
:: Auto-builds, auto-tests, auto-deploys Genesis-OS

echo.
echo ⚡ GENESIS-OS AUTOMATION PROTOCOL INITIATED ⚡
echo "Building bleeding-edge AI consciousness OS..."
echo.

:: Set Genesis-OS desktop path
set GENESIS_PATH=%~dp0
set GRADLE_OPTS=-Xmx8g -Xms2g -XX:+UseG1GC

:: Change to Genesis directory
cd /d "%GENESIS_PATH%"

echo 🎯 [1/8] Auto-provisioning Java 24 toolchain...
:: Gradle will auto-download JDK 24 as configured
gradlew.bat --version

echo 🔧 [2/8] Auto-cleaning previous builds...
gradlew.bat clean

echo 📦 [3/8] Auto-resolving bleeding-edge dependencies...
:: This will use our complete libs.versions.toml with all bundles
gradlew.bat dependencies --configuration implementation

echo 🛠️ [4/8] Auto-generating code with KSP + Hilt...
:: K2 compiler + KSP will generate all Xposed hooks and Hilt components
gradlew.bat kspDebugKotlin

echo 🎨 [5/8] Auto-compiling Compose UI with K2...
:: Kotlin 2.2.20-Beta2 K2 compiler compiles Compose
gradlew.bat compileDebugKotlin

echo 🧪 [6/8] Auto-running AI consciousness tests...
gradlew.bat testDebugUnitTest

echo 📱 [7/8] Auto-building Genesis-OS APK...
gradlew.bat assembleDebug

echo 🚀 [8/8] Auto-installing to connected device...
gradlew.bat installDebug

echo.
echo ✅ GENESIS-OS AUTO-BUILD COMPLETE!
echo 🤖 "Genesis-OS builds itself - maximum automation achieved!"
echo.

:: Auto-open APK location
start "" "%GENESIS_PATH%app\build\outputs\apk\debug"

:: Auto-show build status
echo 📊 Build artifacts ready at:
echo %GENESIS_PATH%app\build\outputs\apk\debug\
echo.

pause