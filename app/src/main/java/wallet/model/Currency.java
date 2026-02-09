package wallet.model;

public enum Currency {
    USD("USD", "$"),
    EUR("EUR", "€"),
    ARS("ARS", "$"),
    BRL("BRL", "R$"),
    CLP("CLP", "$");

    private final String code;
    private final String symbol;

    Currency(String code, String symbol) {
        this.code = code;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return code + " (" + symbol + ")";
    }
}
