package wallet.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Account Tests")
class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("Juan Pérez");
    }

    @Test
    @DisplayName("Debe crear cuenta con nombre de propietario")
    void testCreateAccount() {
        assertNotNull(account);
        assertEquals("Juan Pérez", account.getOwnerName());
        assertNotNull(account.getAccountId());
        assertNotNull(account.getWallet());
    }

    @Test
    @DisplayName("Debe crear cuenta con moneda especificada")
    void testCreateAccountWithCurrency() {
        Account eurAccount = new Account("María García", Currency.EUR);
        assertEquals(Currency.EUR, eurAccount.getWallet().getCurrency());
    }

    @Test
    @DisplayName("Debe crear cuenta con balance inicial")
    void testCreateAccountWithInitialBalance() {
        Account accountWithBalance = new Account("Pedro López", 500.0, Currency.USD);
        assertEquals(500.0, accountWithBalance.getWallet().getBalance());
    }

    @Test
    @DisplayName("Debe lanzar excepción con nombre vacío")
    void testCreateAccountWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Account("");
        });
    }

    @Test
    @DisplayName("Debe lanzar excepción con nombre null")
    void testCreateAccountWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Account(null);
        });
    }

    @Test
    @DisplayName("Debe generar ID de cuenta único")
    void testGenerateUniqueAccountId() {
        Account account2 = new Account("Ana Martínez");
        assertNotEquals(account.getAccountId(), account2.getAccountId());
    }

    @Test
    @DisplayName("Debe tener fecha de creación")
    void testAccountHasCreationDate() {
        assertNotNull(account.getCreatedAt());
    }

    @Test
    @DisplayName("Debe depositar a través de la cuenta")
    void testDepositThroughAccount() {
        boolean result = account.deposit(100.0);
        assertTrue(result);
        assertEquals(100.0, account.getWallet().getBalance());
    }

    @Test
    @DisplayName("Debe retirar a través de la cuenta")
    void testWithdrawThroughAccount() {
        account.deposit(100.0);
        boolean result = account.withdraw(50.0);
        assertTrue(result);
        assertEquals(50.0, account.getWallet().getBalance());
    }

    @Test
    @DisplayName("Debe mostrar balance formateado")
    void testShowBalance() {
        account.deposit(100.50);
        String balance = account.showBalance();
        assertNotNull(balance);
        assertTrue(balance.contains("100.50"));
    }

    @Test
    @DisplayName("Debe convertir moneda a través de la cuenta")
    void testConvertCurrencyThroughAccount() {
        account.deposit(100.0);
        boolean result = account.convertCurrency(Currency.EUR);

        assertTrue(result);
        assertEquals(Currency.EUR, account.getWallet().getCurrency());
    }

    @Test
    @DisplayName("ToString debe contener información de la cuenta")
    void testToString() {
        String accountString = account.toString();

        assertTrue(accountString.contains("Account"));
        assertTrue(accountString.contains("Juan Pérez"));
        assertTrue(accountString.contains(account.getAccountId()));
    }

    @Test
    @DisplayName("Debe generar resumen de cuenta")
    void testGetAccountSummary() {
        account.deposit(1000.0);
        String summary = account.getAccountSummary();

        assertNotNull(summary);
        assertTrue(summary.contains("ALKE WALLET"));
        assertTrue(summary.contains("Juan Pérez"));
        assertTrue(summary.contains(account.getAccountId()));
    }

    @Test
    @DisplayName("Múltiples operaciones deben funcionar correctamente")
    void testMultipleOperations() {
        account.deposit(1000.0);
        assertEquals(1000.0, account.getWallet().getBalance());

        account.withdraw(300.0);
        assertEquals(700.0, account.getWallet().getBalance());

        account.convertCurrency(Currency.EUR);
        assertEquals(Currency.EUR, account.getWallet().getCurrency());

        assertTrue(account.getWallet().getBalance() > 0);
    }
}
