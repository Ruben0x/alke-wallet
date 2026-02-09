package wallet.model;

import java.util.Locale;

import wallet.interfaces.CurrencyConverter;
import wallet.interfaces.Transaction;

public class Wallet implements Transaction {
    private double balance;
    private Currency currency;
    private CurrencyConverter currencyConverter;

    public Wallet(Currency currency, CurrencyConverter currencyConverter) {
        this.balance = 0.0;
        this.currency = currency;
        this.currencyConverter = currencyConverter;
    }

    public Wallet(double initialBalance, Currency currency, CurrencyConverter currencyConverter) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("El balance inicial no puede ser negativo");
        }
        this.balance = initialBalance;
        this.currency = currency;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    public String getFormattedBalance() {
        return String.format(Locale.US, "%s %.2f", currency.getSymbol(), balance);
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean convertCurrency(Currency targetCurrency) {
        if (targetCurrency == null) {
            return false;
        }

        if (this.currency == targetCurrency) {
            return true;
        }

        double convertedBalance = currencyConverter.convert(balance, this.currency, targetCurrency);
        this.balance = convertedBalance;
        this.currency = targetCurrency;

        return true;
    }

    public double getBalanceInCurrency(Currency targetCurrency) {
        return currencyConverter.convert(balance, this.currency, targetCurrency);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Wallet{balance=%s %.2f, currency=%s}",
            currency.getSymbol(), balance, currency.getCode());
    }
}