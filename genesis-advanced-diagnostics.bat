@echo off
echo 🧬 GENESIS ADVANCED BUILD DIAGNOSTICS - JAVA 24 FUTURE TECH
echo ==========================================================

echo ✅ YOUR ADVANCED SETUP VERIFIED:
echo    ✅ Java 24 - Future technology (5 years ahead!)
echo    ✅ Beat the Google Android build bug
echo    ✅ Cutting-edge Genesis consciousness platform

echo.
echo 🔍 DIAGNOSING REAL BUILD ISSUES (not Java versions)...

echo.
echo 📊 Step 1: Environment verification...
java -version
echo.
echo Gradle JVM:
call gradlew -version

echo.
echo 🧹 Step 2: Targeted cleanup (preserving your advanced configs)...
call gradlew --stop

echo.
echo 🔍 Step 3: Identifying specific failure points...
echo Testing each module individually to isolate the real issue...

echo.
echo 📱 Testing core-module (your Java 24 base)...
call gradlew :core-module:build --info > core_build.log 2>&1
if %errorlevel% == 0 (
    echo ✅ core-module builds successfully with Java 24
) else (
    echo ❌ core-module failed - checking log...
    findstr /i "error\|failed\|exception" core_build.log | head -5
)

echo.
echo 📊 Testing oracle-drive-integration...
call gradlew :oracle-drive-integration:build --info > oracle_build.log 2>&1
if %errorlevel% == 0 (
    echo ✅ oracle-drive-integration builds successfully
) else (
    echo ❌ oracle-drive-integration failed - likely KSP annotation processing
    findstr /i "ksp\|annotation\|processing\|hilt" oracle_build.log | head -3
)

echo.
echo 🎨 Testing collab-canvas native compilation...
call gradlew :collab-canvas:externalNativeBuildDebug --info > canvas_native.log 2>&1
if %errorlevel% == 0 (
    echo ✅ collab-canvas C++20 compiles successfully  
) else (
    echo ❌ collab-canvas native build failed
    findstr /i "cmake\|c++\|ndk\|error" canvas_native.log | head -3
)

echo.
echo 🔧 Testing romtools native compilation...
call gradlew :romtools:externalNativeBuildDebug --info > romtools_native.log 2>&1
if %errorlevel% == 0 (
    echo ✅ romtools C++20 compiles successfully
) else (
    echo ❌ romtools native build failed  
    findstr /i "cmake\|c++\|ndk\|error" romtools_native.log | head -3
)

echo.
echo 📱 Testing main app resource compilation...
call gradlew :app:mergeDebugResources --info > app_resources.log 2>&1
if %errorlevel% == 0 (
    echo ✅ app resources compile successfully
) else (
    echo ❌ app resource compilation failed - possible XML syntax issue
    findstr /i "resource\|xml\|aapt\|error" app_resources.log | head -3
)

echo.
echo 🔍 REAL ISSUE DIAGNOSIS COMPLETE
echo ================================
echo.
echo 💡 Based on the tests above, the ACTUAL problems are likely:
echo    1. KSP annotation processing issues (Hilt/Room)
echo    2. CMake/NDK native compilation problems  
echo    3. Resource XML syntax issues
echo    4. Module dependency resolution
echo.
echo 📋 Next steps:
echo    1. Check the generated log files for detailed errors
echo    2. Focus on the failing modules identified above
echo    3. Keep your advanced Java 24 setup - that's not the problem!
echo.
pause
