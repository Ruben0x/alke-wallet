@echo off
REM Script para ejecutar Alke Wallet directamente

echo Compilando el proyecto...
call gradlew build -q

echo.
echo Ejecutando Alke Wallet...
echo.
call gradlew run --console=plain -q

pause
