@echo off
echo =================================
echo  OpenAPI Clean Generation Script
echo =================================
echo.
echo This script will:
echo 1. Clean all OpenAPI generated directories
echo 2. Generate fresh API clients
echo 3. Sync the project
echo.

cd /d "%~dp0"

echo Cleaning OpenAPI generated directories...
call gradlew cleanOpenApiGenerated

echo.
echo Generating all API clients...
call gradlew generateAllApiClients

echo.
echo Syncing project...
call gradlew assemble --dry-run

echo.
echo =================================
echo  OpenAPI Generation Complete!
echo =================================
pause
