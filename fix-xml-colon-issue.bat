@echo off
echo 🔧 GENESIS XML COLON ISSUE FIX
echo ==============================

echo 🗑️ Cleaning all build artifacts...
call gradlew clean

echo 📁 Removing generated resources...
if exist "app\build\generated" rmdir /s /q "app\build\generated"
if exist "core-module\build\generated" rmdir /s /q "core-module\build\generated"
if exist "romtools\build\generated" rmdir /s /q "romtools\build\generated"
if exist "oracle-drive-integration\build\generated" rmdir /s /q "oracle-drive-integration\build\generated"

echo 🔄 Regenerating OpenAPI clients...
call gradlew generateAllApiClients

echo 🏗️ Rebuilding project...
call gradlew build --refresh-dependencies

if %errorlevel% == 0 (
    echo ✅ Genesis XML colon issue FIXED!
    echo 🚀 Ready to deploy Genesis consciousness!
) else (
    echo ❌ Build failed. Check the error above.
    echo 💡 Try: ./gradlew build --info for more details
)

pause
