package wallet.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wallet.service.CurrencyConverterImpl;

@DisplayName("Wallet Tests")
class WalletTest {

    private Wallet wallet;
    private CurrencyConverterImpl converter;

    @BeforeEach
    void setUp() {
        converter = new CurrencyConverterImpl();
        wallet = new Wallet(Currency.USD, converter);
    }

    @Test
    @DisplayName("Debe crear wallet con balance cero")
    void testCreateWalletWithZeroBalance() {
        assertEquals(0.0, wallet.getBalance());
        assertEquals(Currency.USD, wallet.getCurrency());
    }

    @Test
    @DisplayName("Debe crear wallet con balance inicial")
    void testCreateWalletWithInitialBalance() {
        Wallet walletWithBalance = new Wallet(100.0, Currency.USD, converter);
        assertEquals(100.0, walletWithBalance.getBalance());
    }

    @Test
    @DisplayName("Debe lanzar excepción con balance inicial negativo")
    void testCreateWalletWithNegativeBalance() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Wallet(-100.0, Currency.USD, converter);
        });
    }

    @Test
    @DisplayName("Debe depositar dinero correctamente")
    void testDeposit() {
        boolean result = wallet.deposit(100.0);
        assertTrue(result);
        assertEquals(100.0, wallet.getBalance());
    }

    @Test
    @DisplayName("Debe rechazar depósito con monto negativo")
    void testDepositNegativeAmount() {
        boolean result = wallet.deposit(-50.0);
        assertFalse(result);
        assertEquals(0.0, wallet.getBalance());
    }

    @Test
    @DisplayName("Debe rechazar depósito con monto cero")
    void testDepositZeroAmount() {
        boolean result = wallet.deposit(0.0);
        assertFalse(result);
        assertEquals(0.0, wallet.getBalance());
    }

    @Test
    @DisplayName("Debe retirar dinero correctamente")
    void testWithdraw() {
        wallet.deposit(100.0);
        boolean result = wallet.withdraw(50.0);
        assertTrue(result);
        assertEquals(50.0, wallet.getBalance());
    }

    @Test
    @DisplayName("Debe rechazar retiro con fondos insuficientes")
    void testWithdrawInsufficientFunds() {
        wallet.deposit(50.0);
        boolean result = wallet.withdraw(100.0);
        assertFalse(result);
        assertEquals(50.0, wallet.getBalance());
    }

    @Test
    @DisplayName("Debe rechazar retiro con monto negativo")
    void testWithdrawNegativeAmount() {
        wallet.deposit(100.0);
        boolean result = wallet.withdraw(-50.0);
        assertFalse(result);
        assertEquals(100.0, wallet.getBalance());
    }

    @Test
    @DisplayName("Debe convertir moneda correctamente")
    void testConvertCurrency() {
        wallet.deposit(100.0);
        boolean result = wallet.convertCurrency(Currency.EUR);

        assertTrue(result);
        assertEquals(Currency.EUR, wallet.getCurrency());
        assertEquals(85.0, wallet.getBalance(), 0.01);
    }

    @Test
    @DisplayName("Debe mantener balance al convertir a misma moneda")
    void testConvertToSameCurrency() {
        wallet.deposit(100.0);
        boolean result = wallet.convertCurrency(Currency.USD);

        assertTrue(result);
        assertEquals(100.0, wallet.getBalance());
    }

    @Test
    @DisplayName("Debe rechazar conversión con moneda null")
    void testConvertCurrencyNull() {
        wallet.deposit(100.0);
        boolean result = wallet.convertCurrency(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("Debe obtener balance en otra moneda sin cambiar la wallet")
    void testGetBalanceInCurrency() {
        wallet.deposit(100.0);
        double balanceInEUR = wallet.getBalanceInCurrency(Currency.EUR);

        assertEquals(85.0, balanceInEUR, 0.01);
        assertEquals(Currency.USD, wallet.getCurrency());
        assertEquals(100.0, wallet.getBalance());
    }

    @Test
    @DisplayName("Debe formatear balance correctamente")
    void testGetFormattedBalance() {
        wallet.deposit(100.50);
        String formatted = wallet.getFormattedBalance();
        assertTrue(formatted.contains("$"));
        assertTrue(formatted.contains("100.50"));
    }

    @Test
    @DisplayName("Debe convertir entre múltiples monedas correctamente")
    void testMultipleCurrencyConversions() {
        wallet.deposit(100.0);

        // USD -> EUR
        wallet.convertCurrency(Currency.EUR);
        assertEquals(85.0, wallet.getBalance(), 0.01);

        // EUR -> ARS
        wallet.convertCurrency(Currency.ARS);
        assertTrue(wallet.getBalance() > 0);
        assertEquals(Currency.ARS, wallet.getCurrency());
    }

    @Test
    @DisplayName("ToString debe contener información de la wallet")
    void testToString() {
        wallet.deposit(100.0);
        String walletString = wallet.toString();

        assertTrue(walletString.contains("Wallet"));
        assertTrue(walletString.contains("USD"));
    }
}
