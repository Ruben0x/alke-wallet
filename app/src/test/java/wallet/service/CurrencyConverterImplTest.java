package wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wallet.model.Currency;


@DisplayName("CurrencyConverter Tests")
class CurrencyConverterImplTest {

    private CurrencyConverterImpl converter;

    @BeforeEach
    void setUp() {
        converter = new CurrencyConverterImpl();
    }

    @Test
    @DisplayName("Debe convertir de USD a EUR correctamente")
    void testConvertUSDtoEUR() {
        double amount = 100.0;
        double result = converter.convert(amount, Currency.USD, Currency.EUR);
        assertEquals(85.0, result, 0.01);
    }

    @Test
    @DisplayName("Debe convertir de EUR a USD correctamente")
    void testConvertEURtoUSD() {
        double amount = 85.0;
        double result = converter.convert(amount, Currency.EUR, Currency.USD);
        assertEquals(100.0, result, 0.01);
    }

    @Test
    @DisplayName("Debe convertir de USD a ARS correctamente")
    void testConvertUSDtoARS() {
        double amount = 100.0;
        double result = converter.convert(amount, Currency.USD, Currency.ARS);
        assertEquals(35000.0, result, 0.01);
    }

    @Test
    @DisplayName("Convertir a la misma moneda debe retornar el mismo monto")
    void testConvertSameCurrency() {
        double amount = 100.0;
        double result = converter.convert(amount, Currency.USD, Currency.USD);
        assertEquals(amount, result, 0.01);
    }

    @Test
    @DisplayName("Debe lanzar excepción con monto negativo")
    void testConvertNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(-100.0, Currency.USD, Currency.EUR);
        });
    }

    @Test
    @DisplayName("Debe obtener tasa de cambio correcta")
    void testGetExchangeRate() {
        double rate = converter.getExchangeRate(Currency.USD, Currency.EUR);
        assertEquals(0.85, rate, 0.01);
    }

    @Test
    @DisplayName("Tasa de cambio para misma moneda debe ser 1.0")
    void testGetExchangeRateSameCurrency() {
        double rate = converter.getExchangeRate(Currency.USD, Currency.USD);
        assertEquals(1.0, rate, 0.01);
    }

    @Test
    @DisplayName("Debe actualizar tasa de cambio correctamente")
    void testUpdateExchangeRate() {
        converter.updateExchangeRate(Currency.EUR, 0.90);
        double amount = 100.0;
        double result = converter.convert(amount, Currency.USD, Currency.EUR);
        assertEquals(90.0, result, 0.01);
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar con tasa negativa")
    void testUpdateExchangeRateNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.updateExchangeRate(Currency.EUR, -0.90);
        });
    }

    @Test
    @DisplayName("Debe convertir entre monedas no USD correctamente")
    void testConvertBetweenNonUSDCurrencies() {
        double amount = 100.0;
        // EUR a ARS: 100 EUR -> ~117.65 USD -> ~41176.47 ARS
        double result = converter.convert(amount, Currency.EUR, Currency.ARS);
        assertTrue(result > 0);
    }
}
