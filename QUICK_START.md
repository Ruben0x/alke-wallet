# 🚀 Guía Rápida - Alke Wallet

## Inicio Rápido en 3 Pasos

### 1️⃣ Compilar el Proyecto

```bash
# En Windows
gradlew.bat build

# En Linux/Mac
./gradlew build
```

---

### 2️⃣ Ejecutar la Aplicación

**Forma más fácil (Recomendada):**

```bash
# En Windows - doble clic en:
run-app.bat

# En Linux/Mac
./run-app.sh
```

**Usando Gradle:**

```bash
# En Windows
gradlew.bat run --console=plain

# En Linux/Mac
./gradlew run --console=plain
```

**⚠️ Importante**: Usa `--console=plain` para habilitar la entrada interactiva.

---

### 3️⃣ Usar la Aplicación

1. **Crear tu cuenta**:
   - Ingresa tu nombre
   - Selecciona tu moneda preferida (USD, EUR, ARS, BRL, CLP)

2. **Usar el menú**:
   ```
   1. Ver Saldo
   2. Depositar Dinero
   3. Retirar Dinero
   4. Convertir Moneda
   5. Ver Resumen de Cuenta
   6. Demostración Completa (Modo Demo)
   0. Salir
   ```

3. **Probar funcionalidades**:
   - Deposita dinero (ej: 1000)
   - Retira dinero (ej: 200)
   - Convierte a otra moneda
   - Ve las equivalencias en todas las monedas

---

## 🎮 Modo Demostración

Para ver todas las funcionalidades automáticamente:

1. Ejecuta la aplicación
2. Crea una cuenta (cualquier nombre)
3. Selecciona la opción **6** (Demostración Completa)

La demo te mostrará:
- ✅ Creación de cuenta
- ✅ Depósitos y retiros
- ✅ Conversión de monedas
- ✅ Visualización de equivalencias

---

## 🧪 Ejecutar Pruebas

```bash
# Todas las pruebas (39 tests)
./gradlew test

# Ver reporte HTML
# Abrir: app/build/reports/tests/test/index.html
```

---

## ❓ Solución de Problemas

### Error: "No line found"

**Problema**: La aplicación no puede leer la entrada.

**Solución**:
1. Usa `--console=plain` con gradle:
   ```bash
   ./gradlew run --console=plain
   ```
2. O usa los scripts `run-app.bat` / `run-app.sh`

### Error: Java no encontrado

**Problema**: No tienes Java instalado.

**Solución**:
1. Descarga Java JDK 17+ desde: https://adoptium.net/
2. Instala y reinicia tu terminal

### Error de compilación

**Problema**: Dependencias no descargadas.

**Solución**:
```bash
./gradlew clean build --refresh-dependencies
```

---

## 📚 Más Información

- **README completo**: [README.md](README.md)
- **Informe de pruebas**: [TESTING_REPORT.md](TESTING_REPORT.md)
- **Diagrama de clases**: [DIAGRAMA_CLASES.md](DIAGRAMA_CLASES.md)

---

## 💡 Ejemplo de Uso Completo

```
1. Ejecutar: run-app.bat
2. Ingresar nombre: "Maria Rodriguez"
3. Seleccionar moneda: 1 (USD)
4. Opción 2: Depositar $1000
5. Opción 1: Ver saldo (mostrará $1000 USD + equivalencias)
6. Opción 4: Convertir a EUR
7. Opción 3: Retirar €200
8. Opción 5: Ver resumen completo
```

---

**¡Listo para usar Alke Wallet!** 💼✨

¿Necesitas ayuda? Revisa la documentación completa en [README.md](README.md)
