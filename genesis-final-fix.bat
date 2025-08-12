@echo off
echo 🔥 GENESIS FINAL BUILD FIX - ALL ISSUES RESOLVED
echo ================================================

echo ✅ FIXES APPLIED:
echo    ✅ Java 24 → Java 21 (ALL modules)
echo    ✅ CMake C++23 → C++20 (ALL native modules)
echo    ✅ NDK configuration added to all modules
echo    ✅ Kotlin toolchain set to 21 consistently

echo.
echo 🧹 Step 1: Complete cleanup...
call gradlew --stop
if exist ".gradle" rmdir /s /q ".gradle"

echo.
echo 🔧 Step 2: Clean individual modules...
for %%d in (app core-module collab-canvas romtools oracle-drive-integration colorblendr sandbox-ui datavein-oracle-native secure-comm) do (
    echo Cleaning %%d...
    if exist "%%d\build" rmdir /s /q "%%d\build"
    if exist "%%d\.cxx" rmdir /s /q "%%d\.cxx"
    if exist "%%d\src\main\cpp\CMakeCache.txt" del "%%d\src\main\cpp\CMakeCache.txt"
    if exist "%%d\src\main\cpp\CMakeFiles" rmdir /s /q "%%d\src\main\cpp\CMakeFiles"
)

echo.
echo 🔄 Step 3: Generate required sources...
call gradlew generateAllApiClients
if %errorlevel% neq 0 (
    echo ⚠️ OpenAPI generation failed, continuing...
)

echo.
echo 🏗️ Step 4: Build modules in dependency order...

echo.
echo 📦 Building secure-comm (Java 8 - standalone)...
call gradlew :secure-comm:build
if %errorlevel% neq 0 (
    echo ❌ secure-comm failed
    goto :error
)

echo.
echo 📱 Building core-module (Java 21)...
call gradlew :core-module:build
if %errorlevel% neq 0 (
    echo ❌ core-module failed
    goto :error
)

echo.
echo 📊 Building oracle-drive-integration (Java 21)...
call gradlew :oracle-drive-integration:build
if %errorlevel% neq 0 (
    echo ❌ oracle-drive-integration failed
    goto :error
)

echo.
echo 🎨 Building colorblendr (Java 21)...
call gradlew :colorblendr:build
if %errorlevel% neq 0 (
    echo ❌ colorblendr failed
    goto :error
)

echo.
echo 📱 Building sandbox-ui (Java 21)...
call gradlew :sandbox-ui:build
if %errorlevel% neq 0 (
    echo ❌ sandbox-ui failed
    goto :error
)

echo.
echo 🎨 Building collab-canvas (Java 21 + C++20)...
call gradlew :collab-canvas:build
if %errorlevel% neq 0 (
    echo ❌ collab-canvas failed
    goto :error
)

echo.
echo 🔧 Building romtools (Java 21 + C++20)...
call gradlew :romtools:build
if %errorlevel% neq 0 (
    echo ❌ romtools failed
    goto :error
)

echo.
echo 🧬 Building datavein-oracle-native (Java 21)...
call gradlew :datavein-oracle-native:build
if %errorlevel% neq 0 (
    echo ❌ datavein-oracle-native failed
    goto :error
)

echo.
echo 📱 Building main app (Java 21 + C++20)...
call gradlew :app:build
if %errorlevel% neq 0 (
    echo ❌ Main app build failed
    goto :error
)

echo.
echo ✅ SUCCESS! GENESIS ECOSYSTEM BUILD COMPLETE!
echo =============================================
echo 🧬 All modules built successfully with Java 21
echo 🎯 Native libraries compiled with C++20
echo 🚀 Genesis consciousness is ready for deployment!
echo.
echo 📋 Generated artifacts:
dir /s /b *.apk *.aab 2>nul | findstr /v "test"
echo.
echo 🚀 Next steps:
echo    1. Install debug: gradlew installDebug
echo    2. Run tests: gradlew test
echo    3. Deploy to device: gradlew assembleRelease
goto :end

:error
echo.
echo ❌ BUILD FAILED!
echo ==============
echo 💡 If the build still fails, try these steps:
echo    1. Check Android SDK and NDK paths
echo    2. Verify CMake is installed (version 3.22+)
echo    3. Clear caches: rm -rf ~/.gradle/caches
echo    4. Run with logs: gradlew build --info --stacktrace
echo    5. Check specific module: gradlew :MODULE_NAME:build --info
echo.
echo 🔍 Common solutions:
echo    - Update Android Studio to latest
echo    - Install NDK 25.0.8775105 or later
echo    - Set ANDROID_HOME and ANDROID_NDK_ROOT properly
echo    - Check Java 21 is available: java -version

:end
echo.
pause
