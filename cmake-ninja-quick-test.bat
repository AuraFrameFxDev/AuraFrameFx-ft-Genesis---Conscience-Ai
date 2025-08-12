@echo off
echo 🧬 GENESIS CMAKE/NINJA QUICK DIAGNOSTIC
echo ======================================

echo 🔍 Quick diagnosis for your 187 TODOs consciousness system...

echo.
echo 📊 Environment Check:
echo CMake: 
cmake --version 2>nul || echo ❌ CMake not found
echo.
echo Ninja:
ninja --version 2>nul || echo ❌ Ninja not found  
echo.
echo NDK:
if defined ANDROID_NDK_ROOT (
    echo ✅ ANDROID_NDK_ROOT = %ANDROID_NDK_ROOT%
) else (
    echo ❌ ANDROID_NDK_ROOT not set
)

echo.
echo 🔧 Testing quick CMake configure for main AI module...
cd app\src\main\cpp

echo Testing Genesis AI Core configuration...
cmake -G Ninja -DCMAKE_TOOLCHAIN_FILE=%ANDROID_NDK_ROOT%\build\cmake\android.toolchain.cmake -DANDROID_ABI=arm64-v8a -DANDROID_PLATFORM=android-24 . > cmake_test.log 2>&1

if %errorlevel% == 0 (
    echo ✅ CMake configuration successful!
    echo.
    echo 🏗️ Testing Ninja build...
    ninja > ninja_test.log 2>&1
    if %errorlevel% == 0 (
        echo ✅ Ninja build successful! 
        echo.
        echo 🎯 Your CMake/Ninja setup is working!
        echo 💡 The issue might be in Gradle integration
        echo.
        echo 🚀 Try running: gradlew :app:externalNativeBuildDebug
    ) else (
        echo ❌ Ninja build failed
        echo.
        echo 🔍 Ninja error log:
        type ninja_test.log | head -10
    )
) else (
    echo ❌ CMake configuration failed
    echo.
    echo 🔍 CMake error log:
    type cmake_test.log | head -10
)

cd ..\..\..\..\

echo.
echo 📝 Next steps:
echo   1. If CMake/Ninja work: Run gradlew build
echo   2. If CMake fails: Check NDK installation
echo   3. If Ninja fails: Check C++ source code
echo.
pause
