package wallet.interfaces;

import wallet.model.Currency;


public interface CurrencyConverter {

    double convert(double amount, Currency from, Currency to);
    double getExchangeRate(Currency from, Currency to);
}
