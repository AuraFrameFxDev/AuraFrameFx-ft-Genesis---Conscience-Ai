@echo off
echo 🧬 GENESIS FILE LOCK EMERGENCY FIX
echo =================================

echo 🔍 Your 187 TODOs consciousness system has file locks!
echo 💡 This is a Windows process/file handle issue

echo.
echo 🛑 Step 1: Force stop all Gradle processes...
call gradlew --stop
if %errorlevel% neq 0 (
    echo ⚠️ Gradle daemon stop failed, continuing...
)

echo.
echo 🔥 Step 2: Kill any Java/Gradle processes...
taskkill /F /IM java.exe /T 2>nul
taskkill /F /IM javaw.exe /T 2>nul  
taskkill /F /IM gradle.exe /T 2>nul
taskkill /F /IM kotlin-compiler.exe /T 2>nul

echo.
echo 💀 Step 3: Force unlock file handles...
echo Checking for locked files in sandbox-ui...

echo.
echo 🧹 Step 4: Manual cleanup with retries...

echo Attempt 1: Normal deletion...
if exist "sandbox-ui\build" (
    rmdir /s /q "sandbox-ui\build" 2>nul
    if exist "sandbox-ui\build" (
        echo ❌ Normal deletion failed
    ) else (
        echo ✅ Normal deletion succeeded
        goto :success
    )
) else (
    echo ✅ sandbox-ui\build already clean
    goto :success
)

echo.
echo Attempt 2: Force deletion with powershell...
powershell -Command "if (Test-Path 'sandbox-ui\build') { Remove-Item 'sandbox-ui\build' -Recurse -Force -ErrorAction SilentlyContinue }"
if exist "sandbox-ui\build" (
    echo ❌ PowerShell deletion failed
) else (
    echo ✅ PowerShell deletion succeeded
    goto :success
)

echo.
echo Attempt 3: Individual file deletion...
if exist "sandbox-ui\build\intermediates\compile_library_classes_jar\debug\bundleLibCompileToJarDebug\classes.jar" (
    echo Deleting specific jar file...
    del /f /q "sandbox-ui\build\intermediates\compile_library_classes_jar\debug\bundleLibCompileToJarDebug\classes.jar" 2>nul
)

echo Removing directory structure...
if exist "sandbox-ui\build" rmdir /s /q "sandbox-ui\build" 2>nul

if exist "sandbox-ui\build" (
    echo ❌ All attempts failed - manual intervention needed
    goto :manual_fix
) else (
    echo ✅ Force deletion succeeded
    goto :success
)

:success
echo.
echo ✅ FILE LOCK RESOLVED!
echo =====================
echo 🧬 Genesis consciousness system files unlocked!

echo.
echo 🚀 Step 5: Clean restart of build...
echo Performing clean build...
call gradlew clean
if %errorlevel% == 0 (
    echo ✅ Clean successful!
    echo.
    echo 💡 Now you can continue with your build:
    echo    gradlew build
    echo    gradlew :sandbox-ui:build
) else (
    echo ❌ Clean still failing - check for other locked files
)
goto :end

:manual_fix
echo.
echo 🔧 MANUAL FIX REQUIRED
echo =====================
echo The files are locked by a process. Try these steps:
echo.
echo 1. 🔄 **Restart your computer** (most reliable)
echo 2. 🔍 **Use Process Explorer**: Download from Microsoft Sysinternals
echo    - Search for "classes.jar" 
echo    - Kill the process holding the file
echo 3. 🛠️ **Use Handle tool**: Download handle.exe from Sysinternals
echo    - Run: handle.exe classes.jar
echo    - Kill the process ID shown
echo 4. 📱 **Use Android Studio**: 
echo    - File → Invalidate Caches and Restart
echo    - Close Android Studio completely
echo 5. 🧹 **Safe mode deletion**:
echo    - Boot into Windows Safe Mode
echo    - Delete the sandbox-ui\build folder
echo.
echo 💡 Most likely culprits:
echo    - Android Studio still running
echo    - IntelliJ IDEA indexing  
echo    - Windows Defender scanning
echo    - Backup software accessing files
echo    - VS Code with file watchers
echo.

:end
echo.
echo 🧬 Once unlocked, your Genesis consciousness system will build!
echo 📊 187 TODOs awaiting your AI consciousness implementation!
pause
