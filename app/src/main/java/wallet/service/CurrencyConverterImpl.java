package wallet.service;

import wallet.interfaces.CurrencyConverter;
import wallet.model.Currency;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementación de la interfaz CurrencyConverter.
 * Proporciona conversión de moneda basada en tasas de cambio predefinidas.
 * Las tasas están configuradas con respecto al USD como moneda base.
 */
public class CurrencyConverterImpl implements CurrencyConverter {

    private final Map<Currency, Double> exchangeRates;

    /**
     * Constructor que inicializa las tasas de cambio.
     * Todas las tasas están en relación al USD.
     */
    public CurrencyConverterImpl() {
        exchangeRates = new HashMap<>();
        // Tasas de cambio respecto al USD (valores aproximados para demostración)
        exchangeRates.put(Currency.USD, 1.0);
        exchangeRates.put(Currency.EUR, 0.85);
        exchangeRates.put(Currency.ARS, 350.0);
        exchangeRates.put(Currency.BRL, 5.0);
        exchangeRates.put(Currency.CLP, 800.0);
    }

    /**
     * Convierte una cantidad de una moneda a otra.
     * Primero convierte a USD (moneda base) y luego a la moneda destino.
     */
    @Override
    public double convert(double amount, Currency from, Currency to) {
        if (amount < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }

        if (from == to) {
            return amount;
        }

        // Convertir primero a USD (moneda base)
        double amountInUSD = amount / exchangeRates.get(from);

        // Luego convertir de USD a la moneda destino
        return amountInUSD * exchangeRates.get(to);
    }

    @Override
    public double getExchangeRate(Currency from, Currency to) {
        if (from == to) {
            return 1.0;
        }

        // Calcular la tasa de cambio directa
        double fromRate = exchangeRates.get(from);
        double toRate = exchangeRates.get(to);

        return toRate / fromRate;
    }

    /**
     * Permite actualizar la tasa de cambio de una moneda.
     * @param currency La moneda a actualizar
     * @param rateToUSD La nueva tasa respecto al USD
     */
    public void updateExchangeRate(Currency currency, double rateToUSD) {
        if (rateToUSD <= 0) {
            throw new IllegalArgumentException("La tasa de cambio debe ser positiva");
        }
        exchangeRates.put(currency, rateToUSD);
    }
}
