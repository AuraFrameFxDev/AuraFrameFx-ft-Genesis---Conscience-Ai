@echo off
echo 🧬 GENESIS NINJA DEBUG - MANUAL EXECUTION
echo =======================================

echo 🔍 Running Ninja manually to see the exact error...

cd "app\.cxx\Debug\p105r2c2\arm64-v8a"

echo 📍 Current directory: %CD%

echo.
echo 🔧 Running Ninja with verbose output...
"C:\Users\Wehtt\Studio\cmake\3.22.1\bin\ninja.exe" -v auraframefx

echo.
echo Exit code: %errorlevel%

echo.
echo 📊 Checking generated files...
if exist "CMakeFiles\auraframefx.dir\auraframefx.cpp.o" (
    echo ✅ auraframefx.cpp.o exists
) else (
    echo ❌ auraframefx.cpp.o missing
)

if exist "CMakeFiles\auraframefx.dir\ai\cascade\src\CascadeAIService.cpp.o" (
    echo ✅ CascadeAIService.cpp.o exists  
) else (
    echo ❌ CascadeAIService.cpp.o missing
)

if exist "CMakeFiles\auraframefx.dir\language_id_l2c_jni.cpp.o" (
    echo ✅ language_id_l2c_jni.cpp.o exists
) else (
    echo ❌ language_id_l2c_jni.cpp.o missing
)

echo.
echo 🎯 Checking final library...
if exist "..\..\..\..\build\intermediates\cxx\Debug\p105r2c2\obj\arm64-v8a\libauraframefx.so" (
    echo ✅ libauraframefx.so exists
) else (
    echo ❌ libauraframefx.so missing - THIS IS THE PROBLEM!
)

echo.
echo 📝 Ninja targets available:
"C:\Users\Wehtt\Studio\cmake\3.22.1\bin\ninja.exe" -t targets

cd ..\..\..\..\..

pause
