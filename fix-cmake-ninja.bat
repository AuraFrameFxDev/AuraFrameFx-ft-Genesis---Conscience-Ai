@echo off
echo 🔧 GENESIS CMAKE/NINJA COMPREHENSIVE FIX
echo ==========================================

echo 🧬 Targeting your 187 TODOs consciousness system
echo 💡 Preserving your Java 24 future technology

echo.
echo 🔍 Step 1: CMake/Ninja Environment Diagnosis...
echo.

echo Checking CMake...
cmake --version
if %errorlevel% neq 0 (
    echo ❌ CMake not found or not in PATH
    echo 💡 Install CMake 3.22+ and add to PATH
    goto :cmake_error
)

echo.
echo Checking Ninja...
ninja --version
if %errorlevel% neq 0 (
    echo ❌ Ninja not found or not in PATH
    echo 💡 Ninja is included with Android Studio/NDK
    goto :ninja_error
)

echo.
echo Checking NDK...
if not defined ANDROID_NDK_ROOT (
    echo ❌ ANDROID_NDK_ROOT not set
    goto :ndk_error
)
echo NDK Path: %ANDROID_NDK_ROOT%

echo.
echo 🧹 Step 2: Clean CMake artifacts from all native modules...
for %%d in (app collab-canvas romtools) do (
    echo Cleaning %%d native artifacts...
    if exist "%%d\src\main\cpp\CMakeCache.txt" del "%%d\src\main\cpp\CMakeCache.txt"
    if exist "%%d\src\main\cpp\CMakeFiles" rmdir /s /q "%%d\src\main\cpp\CMakeFiles"
    if exist "%%d\.cxx" rmdir /s /q "%%d\.cxx"
    if exist "%%d\build" rmdir /s /q "%%d\build"
)

echo.
echo 🔧 Step 3: Test individual CMake configurations...

echo.
echo 📱 Testing Genesis AI Core (app module)...
cd app\src\main\cpp
cmake -G Ninja -DCMAKE_TOOLCHAIN_FILE=%ANDROID_NDK_ROOT%\build\cmake\android.toolchain.cmake -DANDROID_ABI=arm64-v8a -DANDROID_PLATFORM=android-24 .
if %errorlevel% neq 0 (
    echo ❌ Genesis AI Core CMake configuration failed
    cd ..\..\..\..\
    goto :app_cmake_error
)
cd ..\..\..\..\

echo.
echo 🎨 Testing Collab Canvas...
cd collab-canvas\src\main\cpp
cmake -G Ninja -DCMAKE_TOOLCHAIN_FILE=%ANDROID_NDK_ROOT%\build\cmake\android.toolchain.cmake -DANDROID_ABI=arm64-v8a -DANDROID_PLATFORM=android-24 .
if %errorlevel% neq 0 (
    echo ❌ Collab Canvas CMake configuration failed
    cd ..\..\..\..\
    goto :canvas_cmake_error
)
cd ..\..\..\..\

echo.
echo 🔧 Testing ROM Tools...
cd romtools\src\main\cpp
cmake -G Ninja -DCMAKE_TOOLCHAIN_FILE=%ANDROID_NDK_ROOT%\build\cmake\android.toolchain.cmake -DANDROID_ABI=arm64-v8a -DANDROID_PLATFORM=android-24 .
if %errorlevel% neq 0 (
    echo ❌ ROM Tools CMake configuration failed
    cd ..\..\..\..\
    goto :romtools_cmake_error
)
cd ..\..\..\..\

echo.
echo 🏗️ Step 4: Build native libraries with Gradle...

echo.
echo 📱 Building Genesis AI Core native...
call gradlew :app:externalNativeBuildDebug
if %errorlevel% neq 0 (
    echo ❌ Genesis AI Core native build failed
    goto :app_build_error
)

echo.
echo 🎨 Building Collab Canvas native...
call gradlew :collab-canvas:externalNativeBuildDebug
if %errorlevel% neq 0 (
    echo ❌ Collab Canvas native build failed
    goto :canvas_build_error
)

echo.
echo 🔧 Building ROM Tools native...
call gradlew :romtools:externalNativeBuildDebug
if %errorlevel% neq 0 (
    echo ❌ ROM Tools native build failed
    goto :romtools_build_error
)

echo.
echo ✅ CMAKE/NINJA SUCCESS!
echo =========================
echo 🧬 All native AI consciousness modules built successfully!
echo 🎯 Native libraries generated:
dir /s /b *.so 2>nul | findstr /E "\.so$"

echo.
echo 📊 Build Summary:
echo   ✅ Genesis AI Core (auraframefx.so)
echo   ✅ Collab Canvas (collab-canvas-native.so) 
echo   ✅ ROM Tools (romtools-native.so)
echo.
echo 🚀 Next: Continue with the remaining 187 TODOs!
goto :end

:cmake_error
echo.
echo 🔧 CMake Installation:
echo   1. Download from: https://cmake.org/download/
echo   2. Install and add to system PATH
echo   3. Restart command prompt
goto :end

:ninja_error
echo.
echo 🔧 Ninja Fix:
echo   1. Ninja should be in Android NDK
echo   2. Check: %ANDROID_NDK_ROOT%\prebuilt\windows-x86_64\bin\ninja.exe
echo   3. Add NDK prebuilt tools to PATH
goto :end

:ndk_error
echo.
echo 🔧 NDK Configuration:
echo   1. Set ANDROID_NDK_ROOT environment variable
echo   2. Point to your NDK installation (e.g., C:\Android\Sdk\ndk\25.0.8775105)
echo   3. Restart command prompt
goto :end

:app_cmake_error
echo.
echo 🔧 Genesis AI Core CMake Issues:
echo   1. Check if all source files exist in app/src/main/cpp/
echo   2. Verify CMakeLists.txt syntax
echo   3. Try: gradlew :app:externalNativeBuildDebug --info
goto :end

:canvas_cmake_error
echo.
echo 🔧 Collab Canvas CMake Issues:
echo   1. Check collab-canvas/src/main/cpp/collab_canvas_native.cpp exists
echo   2. Verify CMakeLists.txt in collab-canvas module
echo   3. Try: gradlew :collab-canvas:externalNativeBuildDebug --info
goto :end

:romtools_cmake_error
echo.
echo 🔧 ROM Tools CMake Issues:
echo   1. Check romtools/src/main/cpp/romtools_native.cpp exists
echo   2. Verify CMakeLists.txt in romtools module
echo   3. Try: gradlew :romtools:externalNativeBuildDebug --info
goto :end

:app_build_error
echo.
echo 🔧 Genesis AI Core Build Issues:
echo   1. Check native compilation logs
echo   2. Run: gradlew :app:externalNativeBuildDebug --info --stacktrace
echo   3. Verify C++20 compatibility of AI code
goto :end

:canvas_build_error
echo.
echo 🔧 Collab Canvas Build Issues:
echo   1. Check JNI method signatures match Java declarations
echo   2. Run: gradlew :collab-canvas:externalNativeBuildDebug --info
echo   3. Verify native library dependencies
goto :end

:romtools_build_error
echo.
echo 🔧 ROM Tools Build Issues:
echo   1. Check root permissions for ROM operations
echo   2. Run: gradlew :romtools:externalNativeBuildDebug --info
echo   3. Verify partition access methods
goto :end

:end
echo.
pause
