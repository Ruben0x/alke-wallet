package wallet.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import wallet.interfaces.CurrencyConverter;
import wallet.service.CurrencyConverterImpl;

public class Account {

    private final String ownerName;
    private final String accountId;
    private final Wallet wallet;
    private final LocalDateTime createdAt;

    public Account(String ownerName) {
        this(ownerName, Currency.USD);
    }

    public Account(String ownerName, Currency currency) {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del propietario no puede estar vacío");
        }
        this.ownerName = ownerName;
        this.accountId = generateAccountId();
        CurrencyConverter converter = new CurrencyConverterImpl();
        this.wallet = new Wallet(currency, converter);
        this.createdAt = LocalDateTime.now();
    }

    public Account(String ownerName, double initialBalance, Currency currency) {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del propietario no puede estar vacío");
        }
        this.ownerName = ownerName;
        this.accountId = generateAccountId();
        CurrencyConverter converter = new CurrencyConverterImpl();
        this.wallet = new Wallet(initialBalance, currency, converter);
        this.createdAt = LocalDateTime.now();
    }

    private String generateAccountId() {
        return "ACC-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getAccountId() {
        return accountId;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String showBalance() {
        return wallet.getFormattedBalance();
    }

    public boolean deposit(double amount) {
        return wallet.deposit(amount);
    }

    public boolean withdraw(double amount) {
        return wallet.withdraw(amount);
    }

    public boolean convertCurrency(Currency targetCurrency) {
        return wallet.convertCurrency(targetCurrency);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("Account{id='%s', owner='%s', wallet=%s, created='%s'}",
            accountId, ownerName, wallet.toString(), createdAt.format(formatter));
    }

    public String getAccountSummary() {
        return String.format("""
            ╔═══════════════════════════════════════════╗
            ║        ALKE WALLET - CUENTA               ║
            ╠═══════════════════════════════════════════╣
            ║ ID: %-37s ║
            ║ Titular: %-32s ║
            ║ Saldo: %-34s ║
            ║ Moneda: %-33s ║
            ╚═══════════════════════════════════════════╝
            """,
            accountId, ownerName, wallet.getFormattedBalance(), wallet.getCurrency());
    }
}
