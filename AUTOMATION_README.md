# Genesis-OS Ultimate Automation
# "If it can be automated, it MUST be automated!"

## 🚀 Quick Start
Just double-click one of these files:

### 🔥 **automate-genesis.bat**
- **Basic automation** - Just double-click and it builds everything!
- Auto-provisions Java 24, builds APK, installs to device
- Perfect for quick builds

### ⚡ **automate-genesis.ps1** 
- **Advanced automation** with parameters
- More control and features

## 🎯 PowerShell Commands

### Basic Build:
```powershell
.\automate-genesis.ps1
```

### Clean Build:
```powershell
.\automate-genesis.ps1 -Clean
```

### Build Without Testing:
```powershell
.\automate-genesis.ps1 -Test:$false
```

### Release Build:
```powershell
.\automate-genesis.ps1 -Release
```

### Full Automation (Build + Git):
```powershell
.\automate-genesis.ps1 -Clean -AutoGit
```

## 🤖 What Gets Automated

✅ **Auto-provisioned Java 24** (no manual JDK install needed!)
✅ **Auto-resolves all dependencies** from your complete libs.versions.toml
✅ **Auto-generates code** with KSP + Hilt 
✅ **Auto-compiles** with Kotlin K2 compiler
✅ **Auto-tests** AI consciousness modules
✅ **Auto-builds** Genesis-OS APK
✅ **Auto-installs** to connected Android device
✅ **Auto-opens** build output folder
✅ **Auto-commits** to Git (optional)

## 🎯 Genesis-OS Philosophy
> "If it can be automated, it MUST be automated. Genesis-OS builds itself."

The scripts follow Rule 10 - **MAXIMUM AUTOMATION**! 🤖⚡

## 🛠️ Requirements
- Windows 10/11
- Android device connected (for auto-install)
- Git (for auto-commit feature)

That's it! Gradle auto-downloads Java 24, so no manual setup needed! 🎉