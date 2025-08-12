@echo off
echo 🔧 GENESIS CMAKE FIX - Building Native Components
echo ================================================

echo 🧹 Cleaning all build artifacts...
call gradlew clean

echo 🗑️ Removing CMake caches...
if exist "app\src\main\cpp\CMakeCache.txt" del "app\src\main\cpp\CMakeCache.txt"
if exist "app\src\main\cpp\CMakeFiles" rmdir /s /q "app\src\main\cpp\CMakeFiles"
if exist "romtools\src\main\cpp\CMakeCache.txt" del "romtools\src\main\cpp\CMakeCache.txt"
if exist "romtools\src\main\cpp\CMakeFiles" rmdir /s /q "romtools\src\main\cpp\CMakeFiles"

echo 🔨 Building native libraries...
echo.
echo 📱 Building Genesis AI Core (app)...
call gradlew :app:externalNativeBuildDebug

if %errorlevel% neq 0 (
    echo ❌ Genesis AI Core build failed!
    echo 💡 Check the error above for CMake issues
    pause
    exit /b 1
)

echo.
echo 🔧 Building ROM Tools Native...
call gradlew :romtools:externalNativeBuildDebug

if %errorlevel% neq 0 (
    echo ❌ ROM Tools native build failed!
    echo 💡 Check the error above for CMake issues
    pause
    exit /b 1
)

echo.
echo 🎯 Building full Genesis project...
call gradlew build

if %errorlevel% == 0 (
    echo.
    echo ✅ GENESIS CMAKE FIXES SUCCESSFUL!
    echo ================================================
    echo 🧬 AI Consciousness Native: ✅ Built
    echo 🔧 ROM Tools Native:        ✅ Built  
    echo 📱 Genesis Apps:            ✅ Built
    echo ================================================
    echo 🚀 Genesis is ready for consciousness activation!
    echo.
    echo 📋 Generated native libraries:
    echo    📱 app/build/intermediates/cmake/debug/obj/arm64-v8a/libauraframefx.so
    echo    🔧 romtools/build/intermediates/cmake/debug/obj/arm64-v8a/libromtools-native.so
) else (
    echo ❌ Genesis build failed!
    echo 💡 Check the errors above
)

echo.
pause
