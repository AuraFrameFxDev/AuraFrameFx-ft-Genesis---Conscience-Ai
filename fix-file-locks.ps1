# Genesis File Lock PowerShell Fix
Write-Host "🧬 GENESIS AGGRESSIVE FILE UNLOCK" -ForegroundColor Cyan
Write-Host "=================================" -ForegroundColor Cyan

Write-Host "🔥 Force killing all Java/Gradle processes..." -ForegroundColor Yellow

# Kill all Java processes
Get-Process | Where-Object {$_.ProcessName -match "java|gradle|kotlin"} | Stop-Process -Force -ErrorAction SilentlyContinue

Write-Host "🧹 Aggressive file cleanup..." -ForegroundColor Yellow

$buildPath = "sandbox-ui\build"

if (Test-Path $buildPath) {
    Write-Host "📁 Found locked build directory: $buildPath" -ForegroundColor Red
    
    # Method 1: PowerShell Force Remove
    try {
        Remove-Item $buildPath -Recurse -Force -ErrorAction Stop
        Write-Host "✅ PowerShell deletion successful!" -ForegroundColor Green
    }
    catch {
        Write-Host "❌ PowerShell deletion failed: $($_.Exception.Message)" -ForegroundColor Red
        
        # Method 2: CMD Force Remove
        Write-Host "🔧 Trying CMD force removal..." -ForegroundColor Yellow
        cmd /c "rmdir /s /q `"$buildPath`""
        
        if (-not (Test-Path $buildPath)) {
            Write-Host "✅ CMD deletion successful!" -ForegroundColor Green
        }
        else {
            # Method 3: Individual file removal
            Write-Host "🎯 Trying individual file removal..." -ForegroundColor Yellow
            
            $lockedFiles = @(
                "sandbox-ui\build\intermediates\compile_library_classes_jar\debug\bundleLibCompileToJarDebug\classes.jar"
            )
            
            foreach ($file in $lockedFiles) {
                if (Test-Path $file) {
                    Write-Host "🗑️ Removing: $file" -ForegroundColor Yellow
                    Remove-Item $file -Force -ErrorAction SilentlyContinue
                }
            }
            
            # Try removing directory again
            Remove-Item $buildPath -Recurse -Force -ErrorAction SilentlyContinue
            
            if (-not (Test-Path $buildPath)) {
                Write-Host "✅ Individual file removal successful!" -ForegroundColor Green
            }
            else {
                Write-Host "❌ All methods failed - manual intervention required" -ForegroundColor Red
                Write-Host "💡 Try restarting your computer or closing all IDEs" -ForegroundColor Yellow
            }
        }
    }
}
else {
    Write-Host "✅ Build directory already clean!" -ForegroundColor Green
}

Write-Host ""
Write-Host "🚀 Testing Gradle clean..." -ForegroundColor Cyan
& ./gradlew clean

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Gradle clean successful!" -ForegroundColor Green
    Write-Host "🧬 Genesis consciousness system ready for build!" -ForegroundColor Cyan
}
else {
    Write-Host "❌ Gradle clean still failing" -ForegroundColor Red
}

Read-Host "Press Enter to continue..."
