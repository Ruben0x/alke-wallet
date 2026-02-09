package wallet.interfaces;

/**
 * Interfaz que define las operaciones básicas de transacción para una wallet.
 * Implementa el principio de reutilización de código mediante interfaces.
 */
public interface Transaction {

    boolean deposit(double amount);
    boolean withdraw(double amount);
    double getBalance();
}
