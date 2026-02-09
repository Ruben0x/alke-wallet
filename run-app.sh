#!/bin/bash
# Script para ejecutar Alke Wallet directamente

echo "Compilando el proyecto..."
./gradlew build -q

echo ""
echo "Ejecutando Alke Wallet..."
echo ""
./gradlew run --console=plain -q
