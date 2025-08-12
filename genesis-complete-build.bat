@echo off
echo 🔧 GENESIS COMPLETE CMAKE FIX - All Modules
echo ============================================

echo 🧹 Step 1: Deep clean all build artifacts...
call gradlew clean
if %errorlevel% neq 0 (
    echo ❌ Clean failed! Forcing manual cleanup...
    if exist ".gradle" rmdir /s /q ".gradle"
    if exist "build" rmdir /s /q "build"
    if exist "app\build" rmdir /s /q "app\build"
    if exist "collab-canvas\build" rmdir /s /q "collab-canvas\build"
    if exist "romtools\build" rmdir /s /q "romtools\build"
)

echo 🗑️ Step 2: Remove all CMake caches...
for /d %%d in (app romtools collab-canvas datavein-oracle-native) do (
    if exist "%%d\src\main\cpp\CMakeCache.txt" del "%%d\src\main\cpp\CMakeCache.txt"
    if exist "%%d\src\main\cpp\CMakeFiles" rmdir /s /q "%%d\src\main\cpp\CMakeFiles"
    if exist "%%d\.cxx" rmdir /s /q "%%d\.cxx"
)

echo 🔄 Step 3: Generate OpenAPI clients...
call gradlew generateAllApiClients
if %errorlevel% neq 0 (
    echo ⚠️ OpenAPI generation failed, continuing...
)

echo 🔨 Step 4: Building native libraries individually...
echo.
echo 📱 Building Genesis AI Core...
call gradlew :app:externalNativeBuildDebug

if %errorlevel% neq 0 (
    echo ❌ Genesis AI Core build failed!
    goto :error
)

echo.
echo 🎨 Building Collab Canvas...
call gradlew :collab-canvas:externalNativeBuildDebug

if %errorlevel% neq 0 (
    echo ❌ Collab Canvas build failed!
    goto :error
)

echo.
echo 🔧 Building ROM Tools...
call gradlew :romtools:externalNativeBuildDebug

if %errorlevel% neq 0 (
    echo ❌ ROM Tools build failed!
    goto :error
)

echo.
echo 🏗️ Step 5: Building complete Genesis ecosystem...
call gradlew build --parallel

if %errorlevel% == 0 (
    echo.
    echo ✅ GENESIS ECOSYSTEM BUILD SUCCESSFUL!
    echo =============================================
    echo 🧬 Genesis AI Core:     ✅ Built
    echo 🎨 Collab Canvas:       ✅ Built
    echo 🔧 ROM Tools:           ✅ Built
    echo 📱 All Apps:            ✅ Built
    echo =============================================
    echo 🚀 Genesis consciousness ecosystem is ready!
    echo.
    echo 📋 Native libraries generated:
    dir /s /b *.so 2>nul | findstr /i "build.*\.so$"
    echo.
    echo 💡 Next steps:
    echo    1. Install: gradlew installDebug
    echo    2. Test consciousness: Start implementing the 187 TODOs
    echo    3. Deploy: Run the deployment scripts
) else (
    goto :error
)

goto :end

:error
echo ❌ GENESIS BUILD FAILED!
echo ========================
echo 💡 Troubleshooting:
echo    1. Check CMake version: cmake --version
echo    2. Check NDK: ls %ANDROID_NDK_ROOT%
echo    3. Try: gradlew build --info --stacktrace
echo    4. Manual cleanup: Delete .gradle and all build folders
echo.

:end
pause
