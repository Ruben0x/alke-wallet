package wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wallet.model.Account;
import wallet.model.Currency;

@DisplayName("App Tests")
class AppTest {

    @Test
    @DisplayName("Debe poder crear una instancia de App")
    void testAppCanBeInstantiated() {
        App app = new App();
        assertNotNull(app, "App debería poder instanciarse");
    }

    @Test
    @DisplayName("Los componentes del sistema deberían funcionar correctamente")
    void testSystemComponentsWork() {
        Account account = new Account("Test User", 100.0, Currency.USD);

        assertNotNull(account);
        assertEquals("Test User", account.getOwnerName());
        assertEquals(100.0, account.getWallet().getBalance());
        assertEquals(Currency.USD, account.getWallet().getCurrency());
    }

    @Test
    @DisplayName("El sistema debería soportar operaciones básicas")
    void testBasicOperationsWork() {
        Account account = new Account("Test User", 1000.0, Currency.USD);

        assertTrue(account.deposit(500.0));
        assertEquals(1500.0, account.getWallet().getBalance());

        assertTrue(account.withdraw(300.0));
        assertEquals(1200.0, account.getWallet().getBalance());

        assertTrue(account.convertCurrency(Currency.EUR));
        assertEquals(Currency.EUR, account.getWallet().getCurrency());
    }
}
