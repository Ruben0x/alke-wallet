package wallet;

import java.util.Scanner;

import wallet.model.Account;
import wallet.model.Currency;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static Account currentAccount;

    public static void main(String[] args) {
        System.out.println("""
            ╔══════════════════════════════════════════════════╗
            ║                                                  ║
            ║            BIENVENIDO A ALKE WALLET              ║
            ║         Tu Billetera Digital Segura              ║
            ║                                                  ║
            ╚══════════════════════════════════════════════════╝
            """);

        createAccount();

        boolean running = true;
        while (running) {
            showMenu();
            int option = getIntInput("Seleccione una opción: ");

            switch (option) {
                case 1 -> viewBalance();
                case 2 -> depositMoney();
                case 3 -> withdrawMoney();
                case 4 -> convertCurrency();
                case 5 -> viewAccountSummary();
                case 6 -> demonstrateAllFeatures();
                case 0 -> {
                    System.out.println("\n¡Gracias por usar Alke Wallet! 👋");
                    running = false;
                }
                default -> System.out.println("❌ Opción inválida. Intente nuevamente.");
            }
        }

        scanner.close();
    }


    private static void createAccount() {
        System.out.println("\n📝 Crear Nueva Cuenta");
        System.out.println("─────────────────────");

        System.out.print("Ingrese su nombre: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            name = "Usuario Demo";
        }

        System.out.println("\nSeleccione su moneda preferida:");
        System.out.println("1. USD - Dólar Estadounidense ($)");
        System.out.println("2. EUR - Euro (€)");
        System.out.println("3. ARS - Peso Argentino ($)");
        System.out.println("4. BRL - Real Brasileño (R$)");
        System.out.println("5. CLP - Peso Chileno ($)");

        int currencyChoice = getIntInput("Opción (1-5): ");
        Currency selectedCurrency = switch (currencyChoice) {
            case 2 -> Currency.EUR;
            case 3 -> Currency.ARS;
            case 4 -> Currency.BRL;
            case 5 -> Currency.CLP;
            default -> Currency.USD;
        };

        currentAccount = new Account(name, selectedCurrency);

        System.out.println("\n✅ ¡Cuenta creada exitosamente!");
        System.out.println(currentAccount.getAccountSummary());
        waitForEnter();
    }


    private static void showMenu() {
        System.out.println("""

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
            """);
    }


    private static void viewBalance() {
        System.out.println("\n💰 SALDO ACTUAL");
        System.out.println("─────────────────────");
        System.out.println("Saldo: " + currentAccount.showBalance());
        System.out.println("Moneda: " + currentAccount.getWallet().getCurrency());

        // Mostrar equivalencias en otras monedas
        System.out.println("\n📊 Equivalencias en otras monedas:");
        for (Currency curr : Currency.values()) {
            if (curr != currentAccount.getWallet().getCurrency()) {
                double converted = currentAccount.getWallet().getBalanceInCurrency(curr);
                System.out.printf("   %s: %s %.2f%n", curr.getCode(), curr.getSymbol(), converted);
            }
        }

        waitForEnter();
    }


    private static void depositMoney() {
        System.out.println("\n💵 DEPOSITAR DINERO");
        System.out.println("─────────────────────");
        System.out.println("Moneda actual: " + currentAccount.getWallet().getCurrency());

        double amount = getDoubleInput("Ingrese el monto a depositar: ");

        if (currentAccount.deposit(amount)) {
            System.out.println("✅ Depósito exitoso!");
            System.out.println("Nuevo saldo: " + currentAccount.showBalance());
        } else {
            System.out.println("❌ Error: El monto debe ser mayor a cero.");
        }

        waitForEnter();
    }

    private static void withdrawMoney() {
        System.out.println("\n💸 RETIRAR DINERO");
        System.out.println("─────────────────────");
        System.out.println("Saldo disponible: " + currentAccount.showBalance());

        double amount = getDoubleInput("Ingrese el monto a retirar: ");

        if (currentAccount.withdraw(amount)) {
            System.out.println("✅ Retiro exitoso!");
            System.out.println("Nuevo saldo: " + currentAccount.showBalance());
        } else {
            System.out.println("❌ Error: Fondos insuficientes o monto inválido.");
        }

        waitForEnter();
    }

    private static void convertCurrency() {
        System.out.println("\n🔄 CONVERTIR MONEDA");
        System.out.println("─────────────────────");
        System.out.println("Moneda actual: " + currentAccount.getWallet().getCurrency());
        System.out.println("Saldo actual: " + currentAccount.showBalance());

        System.out.println("\nSeleccione la nueva moneda:");
        System.out.println("1. USD - Dólar Estadounidense ($)");
        System.out.println("2. EUR - Euro (€)");
        System.out.println("3. ARS - Peso Argentino ($)");
        System.out.println("4. BRL - Real Brasileño (R$)");
        System.out.println("5. CLP - Peso Chileno ($)");

        int choice = getIntInput("Opción (1-5): ");
        Currency newCurrency = switch (choice) {
            case 1 -> Currency.USD;
            case 2 -> Currency.EUR;
            case 3 -> Currency.ARS;
            case 4 -> Currency.BRL;
            case 5 -> Currency.CLP;
            default -> null;
        };

        if (newCurrency == null) {
            System.out.println("❌ Opción inválida.");
        } else if (newCurrency == currentAccount.getWallet().getCurrency()) {
            System.out.println("ℹ️  Ya está usando esta moneda.");
        } else {
            double oldBalance = currentAccount.getWallet().getBalance();
            Currency oldCurrency = currentAccount.getWallet().getCurrency();

            if (currentAccount.convertCurrency(newCurrency)) {
                System.out.println("✅ Conversión exitosa!");
                System.out.printf("   %s %.2f %s → %s%n",
                    oldCurrency.getSymbol(), oldBalance, oldCurrency.getCode(),
                    currentAccount.showBalance());
            } else {
                System.out.println("❌ Error en la conversión.");
            }
        }

        waitForEnter();
    }

    private static void viewAccountSummary() {
        System.out.println(currentAccount.getAccountSummary());
        System.out.println("Creada el: " + currentAccount.getCreatedAt().toString());
        waitForEnter();
    }

    private static void demonstrateAllFeatures() {
        System.out.println("""

            ╔══════════════════════════════════════════════════╗
            ║         DEMOSTRACIÓN COMPLETA - ALKE WALLET      ║
            ╚══════════════════════════════════════════════════╝
            """);

        Account demoAccount = new Account("Usuario Demo", 0.0, Currency.USD);
        System.out.println("✅ 1. Cuenta creada: " + demoAccount.getOwnerName());
        System.out.println("   ID: " + demoAccount.getAccountId());
        System.out.println("   Moneda inicial: " + demoAccount.getWallet().getCurrency());

        System.out.println("\n💵 2. Depositando $1000 USD...");
        demoAccount.deposit(1000.0);
        System.out.println("   Saldo: " + demoAccount.showBalance());

        System.out.println("\n💸 3. Retirando $300 USD...");
        demoAccount.withdraw(300.0);
        System.out.println("   Saldo: " + demoAccount.showBalance());

        System.out.println("\n📊 4. Equivalencias del saldo actual:");
        for (Currency curr : Currency.values()) {
            double converted = demoAccount.getWallet().getBalanceInCurrency(curr);
            System.out.printf("   %s: %s %.2f%n", curr.getCode(), curr.getSymbol(), converted);
        }

        System.out.println("\n🔄 5. Convirtiendo saldo a EUR...");
        double usdBalance = demoAccount.getWallet().getBalance();
        demoAccount.convertCurrency(Currency.EUR);
        System.out.printf("   $%.2f USD → %s%.2f EUR%n",
            usdBalance, demoAccount.getWallet().getCurrency().getSymbol(),
            demoAccount.getWallet().getBalance());

        System.out.println("\n🔄 6. Convirtiendo saldo a ARS...");
        double eurBalance = demoAccount.getWallet().getBalance();
        demoAccount.convertCurrency(Currency.ARS);
        System.out.printf("   €%.2f EUR → %s%.2f ARS%n",
            eurBalance, demoAccount.getWallet().getCurrency().getSymbol(),
            demoAccount.getWallet().getBalance());

        System.out.println("\n💵 7. Depositando $50000 ARS adicionales...");
        demoAccount.deposit(50000.0);
        System.out.println("   Saldo: " + demoAccount.showBalance());

        System.out.println("\n📋 8. Resumen Final:");
        System.out.println(demoAccount.getAccountSummary());

        System.out.println("\n✅ ¡Demostración completada exitosamente!");
        System.out.println("   Todas las funcionalidades están operativas:");
        System.out.println("   ✓ Creación de cuenta");
        System.out.println("   ✓ Depósitos");
        System.out.println("   ✓ Retiros");
        System.out.println("   ✓ Conversión de monedas");
        System.out.println("   ✓ Visualización de saldos");

        waitForEnter();
    }


    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Ingrese un número válido.");
            }
        }
    }


    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Ingrese un número válido.");
            }
        }
    }


    private static void waitForEnter() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}
