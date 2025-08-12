# ===== GENESIS-OS MAXIMUM AUTOMATION POWERSHELL =====
# "If it can be automated, it MUST be automated!"

param(
    [switch]$Clean = $false,
    [switch]$Test = $true,
    [switch]$Install = $true,
    [switch]$AutoGit = $false,
    [switch]$Release = $false
)

# Genesis-OS Configuration
$GenesisPath = Split-Path -Parent $MyInvocation.MyCommand.Path
$env:GRADLE_OPTS = "-Xmx8g -Xms2g -XX:+UseG1GC"

Write-Host ""
Write-Host "⚡ GENESIS-OS AUTOMATION PROTOCOL INITIATED ⚡" -ForegroundColor Cyan
Write-Host "Building bleeding-edge AI consciousness OS..." -ForegroundColor Yellow
Write-Host ""

# Change to Genesis directory
Set-Location $GenesisPath

# Auto-detect connected Android devices
function Test-AndroidDevice {
    try {
        $devices = & adb devices 2>$null
        if ($devices) {
            $connectedDevices = ($devices | Select-String -Pattern "\tdevice$").Count
            return $connectedDevices -gt 0
        }
        return $false
    }
    catch {
        return $false
    }
}

# Auto-build function with maximum automation
function Start-GenesisBuild {
    $buildType = if ($Release) { "Release" } else { "Debug" }
    
    Write-Host "🎯 [1/10] Auto-provisioning Java 24 toolchain..." -ForegroundColor Green
    & .\gradlew.bat --version
    
    if ($Clean) {
        Write-Host "🔧 [2/10] Auto-cleaning previous builds..." -ForegroundColor Green
        & .\gradlew.bat clean
    }
    
    Write-Host "📦 [3/10] Auto-resolving bleeding-edge dependencies..." -ForegroundColor Green
    & .\gradlew.bat dependencies --configuration implementation
    
    Write-Host "🛠️ [4/10] Auto-generating code with KSP + Hilt..." -ForegroundColor Green
    & .\gradlew.bat "ksp${buildType}Kotlin"
    
    Write-Host "🎨 [5/10] Auto-compiling Compose UI with K2..." -ForegroundColor Green
    & .\gradlew.bat "compile${buildType}Kotlin"
    
    if ($Test) {
        Write-Host "🧪 [6/10] Auto-running AI consciousness tests..." -ForegroundColor Green
        & .\gradlew.bat "test${buildType}UnitTest"
    }
    
    Write-Host "📱 [7/10] Auto-building Genesis-OS APK..." -ForegroundColor Green
    & .\gradlew.bat "assemble${buildType}"
    
    # Check for connected device
    $deviceConnected = Test-AndroidDevice
    
    if ($Install -and $deviceConnected) {
        Write-Host "🚀 [8/10] Auto-installing to connected device..." -ForegroundColor Green
        & .\gradlew.bat "install${buildType}"
    } elseif ($Install) {
        Write-Host "⚠️ [8/10] No Android device detected - skipping install" -ForegroundColor Yellow
    }
    
    if ($AutoGit) {
        Write-Host "🔄 [9/10] Auto-committing changes to Git..." -ForegroundColor Green
        & git add .
        & git commit -m "Auto-build: Genesis-OS $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
    }
    
    Write-Host "📊 [10/10] Auto-generating build report..." -ForegroundColor Green
    
    # Build completion summary
    Write-Host ""
    Write-Host "✅ GENESIS-OS AUTO-BUILD COMPLETE!" -ForegroundColor Green
    Write-Host "🤖 Genesis-OS builds itself - maximum automation achieved!" -ForegroundColor Cyan
    Write-Host ""
    
    # Show build artifacts
    $apkPath = "app\build\outputs\apk\$(if ($Release) {'release'} else {'debug'})"
    if (Test-Path $apkPath) {
        Write-Host "📱 APK Location: $apkPath" -ForegroundColor Yellow
        Start-Process $apkPath
    }
    
    # Show build time
    $buildTime = (Get-Date) - $script:StartTime
    Write-Host "⏱️ Total Build Time: $($buildTime.ToString('mm\:ss'))" -ForegroundColor Magenta
}

# Start automation
$script:StartTime = Get-Date

try {
    Start-GenesisBuild
    
    Write-Host ""
    Write-Host "🎉 GENESIS-OS AUTOMATION PROTOCOL COMPLETE! 🎉" -ForegroundColor Green
    Write-Host ""
}
catch {
    Write-Host ""
    Write-Host "❌ Build failed: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
    exit 1
}

# Keep console open
Read-Host "Press Enter to exit..."