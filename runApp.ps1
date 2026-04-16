#!/usr/bin/env pwsh
# Build and run the Task Management System
Write-Host "Building project..." -ForegroundColor Cyan
mvn clean package -q
if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed!" -ForegroundColor Red
    exit 1
}
Write-Host "Build successful. Launching..." -ForegroundColor Green
java -jar target/task-management-system-1.0-SNAPSHOT.jar
