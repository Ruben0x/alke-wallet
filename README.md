# 💼 Alke Wallet - Billetera Digital

![Java](https://img.shields.io/badge/Java-17+-blue.svg)
![Gradle](https://img.shields.io/badge/Gradle-8.0+-green.svg)
![JUnit](https://img.shields.io/badge/JUnit-5-orange.svg)

**Alke Wallet** es una aplicación de billetera digital desarrollada en Java que permite a los usuarios gestionar sus activos financieros de manera segura y conveniente, con soporte para múltiples monedas y conversión entre ellas.

---

## 📋 Descripción del Proyecto

Este proyecto fue desarrollado como parte del módulo de evaluación de programación orientada a objetos. Proporciona una solución completa para administrar fondos digitales con las siguientes características:

- ✅ Creación de cuentas de usuario
- ✅ Visualización de saldo en tiempo real
- ✅ Depósitos y retiros de dinero
- ✅ Conversión entre múltiples monedas (USD, EUR, ARS, BRL, CLP)
- ✅ Validación de transacciones
- ✅ Interfaz de usuario por consola interactiva

---

## 🎯 Objetivos del Proyecto

El objetivo principal es desarrollar una billetera digital funcional que:

1. Permita gestionar activos financieros de forma segura
2. Implemente el paradigma de Programación Orientada a Objetos
3. Utilice interfaces para la reutilización de código
4. Incluya pruebas unitarias exhaustivas
5. Proporcione una experiencia de usuario clara y confiable

---

## 🏗️ Arquitectura del Proyecto

### Estructura de Paquetes

```
wallet/
├── model/                  # Modelos de dominio
│   ├── Account.java       # Cuenta de usuario
│   ├── Currency.java      # Enum de monedas
│   └── Wallet.java        # Billetera digital
├── interfaces/            # Interfaces para reutilización
│   ├── Transaction.java   # Operaciones de transacción
│   └── CurrencyConverter.java  # Conversión de monedas
├── service/               # Lógica de negocio
│   └── CurrencyConverterImpl.java  # Implementación de conversión
└── App.java              # Aplicación principal
```

### Diagrama de Clases

```
┌─────────────────────┐
│   <<interface>>     │
│   Transaction       │
├─────────────────────┤
│ + deposit()         │
│ + withdraw()        │
│ + getBalance()      │
└──────────▲──────────┘
           │
           │ implements
           │
┌──────────┴──────────────────────┐
│         Wallet                   │
├──────────────────────────────────┤
│ - balance: double                │
│ - currency: Currency             │
│ - converter: CurrencyConverter   │
├──────────────────────────────────┤
│ + deposit(amount): boolean       │
│ + withdraw(amount): boolean      │
│ + convertCurrency(target): bool  │
│ + getBalanceInCurrency(): double │
└──────────────────────────────────┘
           ▲
           │
           │ has-a
           │
┌──────────┴──────────┐
│      Account        │
├─────────────────────┤
│ - ownerName: String │
│ - accountId: String │
│ - wallet: Wallet    │
│ - createdAt: Date   │
├─────────────────────┤
│ + deposit()         │
│ + withdraw()        │
│ + showBalance()     │
│ + convertCurrency() │
└─────────────────────┘

┌────────────────────┐       ┌──────────────────────┐
│  <<enumeration>>   │       │   <<interface>>      │
│     Currency       │       │  CurrencyConverter   │
├────────────────────┤       ├──────────────────────┤
│ USD                │       │ + convert()          │
│ EUR                │       │ + getExchangeRate()  │
│ ARS                │       └──────────▲───────────┘
│ BRL                │                  │
│ CLP                │                  │ implements
└────────────────────┘                  │
                          ┌─────────────┴────────────┐
                          │ CurrencyConverterImpl    │
                          ├──────────────────────────┤
                          │ - exchangeRates: Map     │
                          ├──────────────────────────┤
                          │ + convert()              │
                          │ + getExchangeRate()      │
                          │ + updateExchangeRate()   │
                          └──────────────────────────┘
```

---

## 🚀 Funcionalidades Principales

### 1. Administración de Fondos

- **Crear Cuenta**: Registro de usuarios con nombre y moneda preferida
- **Ver Saldo**: Consulta de saldo actual con formato de moneda
- **Depositar Dinero**: Incremento del saldo con validación
- **Retirar Dinero**: Retiro de fondos con verificación de saldo suficiente

### 2. Conversión de Moneda

- **Soporte Multi-Moneda**: USD, EUR, ARS, BRL, CLP
- **Conversión en Tiempo Real**: Cambio de moneda con tasas predefinidas
- **Equivalencias**: Visualización de saldo en todas las monedas disponibles

### 3. Validaciones de Seguridad

- ✓ Validación de montos negativos
- ✓ Verificación de fondos suficientes
- ✓ Validación de nombres de usuario
- ✓ Manejo de errores robusto

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Java 17+
- **Build Tool**: Gradle 8.0+
- **Testing**: JUnit 5 (Jupiter)
- **Paradigma**: Programación Orientada a Objetos
- **Principios**: SOLID, Clean Code

---

## 📦 Instalación y Ejecución

### Requisitos Previos

- Java JDK 17 o superior
- Gradle 8.0 o superior (incluido en el wrapper)

### Pasos de Instalación

1. **Clonar el repositorio**:

   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd wallet
   ```

2. **Compilar el proyecto**:

   ```bash
   ./gradlew build
   ```

3. **Ejecutar la aplicación**:

   **Opción 1: Usando el script (Recomendado)**

   ```bash
   # En Windows
   run-app.bat

   # En Linux/Mac
   chmod +x run-app.sh
   ./run-app.sh
   ```

   **Opción 2: Usando Gradle directamente**

   ```bash
   # En Linux/Mac
   ./gradlew run --console=plain

   # En Windows
   gradlew.bat run --console=plain
   ```

4. **Ejecutar las pruebas**:
   ```bash
   ./gradlew test
   ```

---

## 🧪 Pruebas Unitarias

El proyecto incluye pruebas exhaustivas para garantizar la calidad:

### Cobertura de Pruebas

- ✅ **WalletTest**: 13 pruebas para operaciones de billetera
- ✅ **AccountTest**: 12 pruebas para gestión de cuentas
- ✅ **CurrencyConverterImplTest**: 11 pruebas para conversión de monedas

### Ejecutar Pruebas

```bash
# Ejecutar todas las pruebas
./gradlew test

# Ver reporte de pruebas
./gradlew test --info

# Generar reporte HTML
./gradlew test jacocoTestReport
```

### Casos de Prueba Principales

1. **Creación de Cuenta**:
   - Cuenta con nombre válido
   - Validación de nombre vacío/null
   - Generación de ID único

2. **Operaciones de Transacción**:
   - Depósito exitoso
   - Retiro con fondos suficientes
   - Rechazo de montos negativos
   - Validación de saldo insuficiente

3. **Conversión de Moneda**:
   - Conversión entre monedas diferentes
   - Conversión a la misma moneda
   - Tasas de cambio correctas
   - Actualización de tasas

---

## 💡 Uso de la Aplicación

### Menú Principal

Al ejecutar la aplicación, se presenta un menú interactivo:

```
╔══════════════════════════════════════════════════╗
║              MENÚ PRINCIPAL                      ║
╠══════════════════════════════════════════════════╣
║  1. Ver Saldo                                    ║
║  2. Depositar Dinero                             ║
║  3. Retirar Dinero                               ║
║  4. Convertir Moneda                             ║
║  5. Ver Resumen de Cuenta                        ║
║  6. Demostración Completa (Modo Demo)            ║
║  0. Salir                                        ║
╚══════════════════════════════════════════════════╝
```

### Ejemplo de Uso

1. **Crear cuenta** con nombre y moneda preferida
2. **Depositar** dinero inicial
3. **Ver saldo** en múltiples monedas
4. **Convertir** a otra moneda si es necesario
5. **Retirar** dinero cuando lo necesites

### Modo Demostración

La opción 6 ejecuta una demostración automática que muestra todas las funcionalidades:

- Creación de cuenta
- Depósitos y retiros
- Conversiones de moneda
- Visualización de equivalencias
- Resumen completo

---

## 📊 Tasas de Cambio

Las tasas están configuradas respecto al USD como moneda base:

| Moneda | Código | Tasa (1 USD) |
| ------ | ------ | ------------ |
| USD    | USD    | 1.00         |
| EUR    | EUR    | 0.85         |
| ARS    | ARS    | 350.00       |
| BRL    | BRL    | 5.00         |
| CLP    | CLP    | 800.00       |

> **Nota**: Las tasas son valores aproximados para demostración y pueden ser actualizadas mediante `CurrencyConverterImpl.updateExchangeRate()`.

---

## 🎓 Principios de Diseño Aplicados

### Programación Orientada a Objetos

1. **Encapsulamiento**: Datos privados con getters/setters apropiados
2. **Abstracción**: Interfaces `Transaction` y `CurrencyConverter`
3. **Herencia**: Implementación de interfaces
4. **Polimorfismo**: Métodos sobrecargados y sobreescritos

### Patrones de Diseño

- **Strategy Pattern**: CurrencyConverter como estrategia de conversión
- **Dependency Injection**: CurrencyConverter inyectado en Wallet
- **Value Object**: Currency como enum inmutable

### Principios SOLID

- ✅ **Single Responsibility**: Cada clase tiene una responsabilidad única
- ✅ **Open/Closed**: Extensible mediante interfaces
- ✅ **Liskov Substitution**: Implementaciones intercambiables
- ✅ **Interface Segregation**: Interfaces específicas y cohesivas
- ✅ **Dependency Inversion**: Dependencia de abstracciones

---

## 📈 Mejoras Futuras

- [ ] Persistencia de datos (base de datos)
- [ ] Historial de transacciones
- [ ] Transferencias entre cuentas
- [ ] Autenticación de usuarios
- [ ] Integración con APIs de tasas de cambio en tiempo real
- [ ] Interfaz gráfica (GUI)
- [ ] Exportación de reportes (PDF, CSV)
- [ ] Notificaciones por email
- [ ] Límites de transacción configurables
- [ ] Soporte para criptomonedas

---

## 📄 Licencia

Este proyecto es de código abierto y está disponible para fines educativos.

---

## ✅ Checklist de Requerimientos

### Requerimientos Generales

- [x] Crear cuenta
- [x] Ver saldo disponible
- [x] Realizar ingreso de dinero
- [x] Realizar retiro de dinero
- [x] Conversión de moneda

### Requerimientos Técnicos

- [x] Backend en Java con POO
- [x] Uso de interfaces para reutilización
- [x] Diagrama de clases
- [x] Pruebas unitarias

### Entregables

- [x] Código fuente completo
- [x] Documentación técnica
- [x] Prototipo funcional
- [x] Informe de pruebas
- [x] Presentación final (README)

---

**¡Gracias por usar Alke Wallet!** 💼✨
