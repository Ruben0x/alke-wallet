# 📐 Diagrama de Clases - Alke Wallet

## Diagrama UML Completo

```
┌─────────────────────────────────────────────────────────────┐
│                    <<enumeration>>                          │
│                       Currency                              │
├─────────────────────────────────────────────────────────────┤
│ + USD: Currency                                             │
│ + EUR: Currency                                             │
│ + ARS: Currency                                             │
│ + BRL: Currency                                             │
│ + CLP: Currency                                             │
├─────────────────────────────────────────────────────────────┤
│ - code: String                                              │
│ - symbol: String                                            │
├─────────────────────────────────────────────────────────────┤
│ + Currency(code: String, symbol: String)                    │
│ + getCode(): String                                         │
│ + getSymbol(): String                                       │
│ + toString(): String                                        │
└─────────────────────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────┐
│                    <<interface>>                            │
│                     Transaction                             │
├─────────────────────────────────────────────────────────────┤
│ + deposit(amount: double): boolean                          │
│ + withdraw(amount: double): boolean                         │
│ + getBalance(): double                                      │
└─────────────────────────────────────────────────────────────┘
                            △
                            │
                            │ implements
                            │
┌─────────────────────────────────────────────────────────────┐
│                        Wallet                               │
├─────────────────────────────────────────────────────────────┤
│ - balance: double                                           │
│ - currency: Currency                                        │
│ - currencyConverter: CurrencyConverter                      │
├─────────────────────────────────────────────────────────────┤
│ + Wallet(currency: Currency, converter: CurrencyConverter)  │
│ + Wallet(balance: double, currency: Currency, ...)          │
│ + getBalance(): double                                      │
│ + getFormattedBalance(): String                             │
│ + getCurrency(): Currency                                   │
│ + deposit(amount: double): boolean                          │
│ + withdraw(amount: double): boolean                         │
│ + convertCurrency(targetCurrency: Currency): boolean        │
│ + getBalanceInCurrency(targetCurrency: Currency): double    │
│ + toString(): String                                        │
└─────────────────────────────────────────────────────────────┘
                            △
                            │
                            │ has-a (composición)
                            │
┌─────────────────────────────────────────────────────────────┐
│                        Account                              │
├─────────────────────────────────────────────────────────────┤
│ - ownerName: String                                         │
│ - accountId: String                                         │
│ - wallet: Wallet                                            │
│ - createdAt: LocalDateTime                                  │
├─────────────────────────────────────────────────────────────┤
│ + Account(ownerName: String)                                │
│ + Account(ownerName: String, currency: Currency)            │
│ + Account(ownerName: String, balance: double, currency: ...) │
│ - generateAccountId(): String                               │
│ + getOwnerName(): String                                    │
│ + getAccountId(): String                                    │
│ + getWallet(): Wallet                                       │
│ + getCreatedAt(): LocalDateTime                             │
│ + showBalance(): String                                     │
│ + deposit(amount: double): boolean                          │
│ + withdraw(amount: double): boolean                         │
│ + convertCurrency(targetCurrency: Currency): boolean        │
│ + toString(): String                                        │
│ + getAccountSummary(): String                               │
└─────────────────────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────┐
│                    <<interface>>                            │
│                  CurrencyConverter                          │
├─────────────────────────────────────────────────────────────┤
│ + convert(amount: double, from: Currency, to: Currency)     │
│   : double                                                  │
│ + getExchangeRate(from: Currency, to: Currency): double     │
└─────────────────────────────────────────────────────────────┘
                            △
                            │
                            │ implements
                            │
┌─────────────────────────────────────────────────────────────┐
│                 CurrencyConverterImpl                       │
├─────────────────────────────────────────────────────────────┤
│ - exchangeRates: Map<Currency, Double>                      │
├─────────────────────────────────────────────────────────────┤
│ + CurrencyConverterImpl()                                   │
│ + convert(amount: double, from: Currency, to: Currency)     │
│   : double                                                  │
│ + getExchangeRate(from: Currency, to: Currency): double     │
│ + updateExchangeRate(currency: Currency, rate: double)      │
│   : void                                                    │
└─────────────────────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────┐
│                          App                                │
├─────────────────────────────────────────────────────────────┤
│ - scanner: Scanner {static}                                 │
│ - currentAccount: Account {static}                          │
├─────────────────────────────────────────────────────────────┤
│ + main(args: String[]): void {static}                       │
│ - createAccount(): void {static}                            │
│ - showMenu(): void {static}                                 │
│ - viewBalance(): void {static}                              │
│ - depositMoney(): void {static}                             │
│ - withdrawMoney(): void {static}                            │
│ - convertCurrency(): void {static}                          │
│ - viewAccountSummary(): void {static}                       │
│ - demonstrateAllFeatures(): void {static}                   │
│ - getIntInput(prompt: String): int {static}                 │
│ - getDoubleInput(prompt: String): double {static}           │
│ - waitForEnter(): void {static}                             │
└─────────────────────────────────────────────────────────────┘
```

---

## Relaciones entre Clases

### 1. Herencia e Implementación

```
Transaction (Interface)
    ↑
    │ implements
    │
  Wallet

CurrencyConverter (Interface)
    ↑
    │ implements
    │
CurrencyConverterImpl
```

**Descripción**:
- `Wallet` implementa la interfaz `Transaction` para operaciones básicas
- `CurrencyConverterImpl` implementa `CurrencyConverter` para conversiones

---

### 2. Composición

```
Account
    ◆────> Wallet
    │
    └────> Currency (a través de Wallet)

Wallet
    ◆────> CurrencyConverter
    │
    └────> Currency
```

**Descripción**:
- `Account` **contiene** un `Wallet` (relación de composición fuerte)
- `Wallet` **contiene** un `CurrencyConverter` y un `Currency`
- La composición significa que el ciclo de vida del objeto contenido depende del contenedor

---

### 3. Dependencias

```
Wallet ────> CurrencyConverter (usa)
Wallet ────> Currency (usa)
Account ────> Currency (usa indirectamente)
CurrencyConverterImpl ────> Currency (usa)
App ────> Account (usa)
App ────> Currency (usa)
```

**Descripción**:
- Las clases usan otras clases sin contenerlas permanentemente
- Representan dependencias funcionales

---

## Descripción Detallada por Componente

### 1. Currency (Enum)

**Propósito**: Representar las diferentes monedas soportadas por el sistema.

**Atributos**:
- `code: String` - Código ISO de la moneda (USD, EUR, etc.)
- `symbol: String` - Símbolo de la moneda ($, €, etc.)

**Métodos**:
- `Currency(code, symbol)` - Constructor del enum
- `getCode(): String` - Obtiene el código
- `getSymbol(): String` - Obtiene el símbolo
- `toString(): String` - Representación en texto

**Valores**:
- `USD` - Dólar estadounidense
- `EUR` - Euro
- `ARS` - Peso argentino
- `BRL` - Real brasileño
- `CLP` - Peso chileno

---

### 2. Transaction (Interface)

**Propósito**: Definir el contrato para operaciones de transacción.

**Métodos**:
- `deposit(amount: double): boolean` - Depositar dinero
- `withdraw(amount: double): boolean` - Retirar dinero
- `getBalance(): double` - Obtener saldo

**Implementada por**: `Wallet`

---

### 3. Wallet (Class)

**Propósito**: Gestionar el saldo y las operaciones financieras de un usuario.

**Atributos**:
- `balance: double` - Saldo actual
- `currency: Currency` - Moneda actual
- `currencyConverter: CurrencyConverter` - Conversor de monedas

**Constructores**:
- `Wallet(currency, converter)` - Wallet con balance 0
- `Wallet(balance, currency, converter)` - Wallet con balance inicial

**Métodos principales**:
- Operaciones de transacción (heredadas de Transaction)
- `convertCurrency(target)` - Cambia la moneda de la wallet
- `getBalanceInCurrency(target)` - Ve saldo en otra moneda sin cambiar
- `getFormattedBalance()` - Saldo formateado con símbolo

---

### 4. Account (Class)

**Propósito**: Representar una cuenta de usuario con información personal y wallet.

**Atributos**:
- `ownerName: String` - Nombre del propietario
- `accountId: String` - ID único de la cuenta
- `wallet: Wallet` - Billetera asociada
- `createdAt: LocalDateTime` - Fecha de creación

**Constructores**:
- `Account(ownerName)` - Cuenta con moneda USD por defecto
- `Account(ownerName, currency)` - Cuenta con moneda específica
- `Account(ownerName, balance, currency)` - Cuenta con balance inicial

**Métodos principales**:
- Delegación a Wallet: `deposit()`, `withdraw()`, `convertCurrency()`
- Visualización: `showBalance()`, `getAccountSummary()`
- Utilidad: `generateAccountId()` - Genera ID único

---

### 5. CurrencyConverter (Interface)

**Propósito**: Definir el contrato para conversión de monedas.

**Métodos**:
- `convert(amount, from, to): double` - Convierte entre monedas
- `getExchangeRate(from, to): double` - Obtiene tasa de cambio

**Implementada por**: `CurrencyConverterImpl`

---

### 6. CurrencyConverterImpl (Class)

**Propósito**: Implementar la lógica de conversión de monedas.

**Atributos**:
- `exchangeRates: Map<Currency, Double>` - Tasas de cambio respecto a USD

**Constructor**:
- `CurrencyConverterImpl()` - Inicializa tasas predefinidas

**Métodos**:
- `convert(amount, from, to)` - Convierte usando USD como base
- `getExchangeRate(from, to)` - Calcula tasa directa
- `updateExchangeRate(currency, rate)` - Actualiza tasa de una moneda

**Lógica de conversión**:
1. Convierte el monto a USD (moneda base)
2. Convierte de USD a la moneda destino

---

### 7. App (Class)

**Propósito**: Aplicación principal con interfaz de usuario por consola.

**Atributos estáticos**:
- `scanner: Scanner` - Para entrada de usuario
- `currentAccount: Account` - Cuenta activa

**Métodos estáticos**:
- `main()` - Punto de entrada
- Métodos de menú: `createAccount()`, `showMenu()`, etc.
- Operaciones: `depositMoney()`, `withdrawMoney()`, `convertCurrency()`
- Utilidades: `getIntInput()`, `getDoubleInput()`, `waitForEnter()`
- Demo: `demonstrateAllFeatures()` - Muestra todas las funcionalidades

---

## Principios de Diseño Aplicados

### 1. Encapsulamiento

- Todos los atributos son privados (`-`)
- Acceso controlado mediante getters/setters públicos (`+`)
- Validación en métodos de negocio

### 2. Abstracción

- Interfaces `Transaction` y `CurrencyConverter`
- Separación de contratos e implementaciones
- Enum `Currency` como abstracción de monedas

### 3. Composición sobre Herencia

- `Account` compone `Wallet` en lugar de heredar
- `Wallet` compone `CurrencyConverter`
- Favorece flexibilidad y reutilización

### 4. Dependency Injection

- `Wallet` recibe `CurrencyConverter` por constructor
- Facilita testing y desacoplamiento
- Permite cambiar implementaciones

### 5. Single Responsibility

- `Currency`: Solo representa monedas
- `Wallet`: Solo gestiona balance y operaciones
- `Account`: Solo gestiona información de usuario
- `CurrencyConverter`: Solo convierte monedas
- `App`: Solo maneja interacción con usuario

---

## Flujo de Datos Principal

### Operación de Depósito

```
Usuario → App.depositMoney()
           ↓
       Account.deposit()
           ↓
       Wallet.deposit()
           ↓
       balance += amount
```

### Operación de Conversión

```
Usuario → App.convertCurrency()
           ↓
       Account.convertCurrency()
           ↓
       Wallet.convertCurrency()
           ↓
       CurrencyConverter.convert()
           ↓
       CurrencyConverterImpl.convert()
           ↓
       balance = convertedAmount
       currency = newCurrency
```

---

## Ventajas del Diseño

1. **Modularidad**: Cada clase tiene una responsabilidad clara
2. **Reutilización**: Las interfaces permiten diferentes implementaciones
3. **Mantenibilidad**: Cambios localizados en componentes específicos
4. **Testabilidad**: Cada componente puede probarse independientemente
5. **Extensibilidad**: Fácil agregar nuevas monedas o conversores
6. **Desacoplamiento**: Las clases dependen de abstracciones, no de implementaciones

---

## Posibles Extensiones

### Nuevas Clases Sugeridas

```
┌─────────────────────────┐
│  TransactionHistory     │
├─────────────────────────┤
│ - transactions: List    │
│ + addTransaction()      │
│ + getHistory()          │
└─────────────────────────┘

┌─────────────────────────┐
│      Transfer           │
├─────────────────────────┤
│ + transfer(from, to)    │
└─────────────────────────┘

┌─────────────────────────┐
│    UserAuthentication   │
├─────────────────────────┤
│ + login()               │
│ + logout()              │
│ + verifyPassword()      │
└─────────────────────────┘
```

---

**Diagrama creado para**: Alke Wallet v1.0.0
**Fecha**: 2026-02-08
**Herramienta**: UML Textual
**Notación**: UML 2.5
