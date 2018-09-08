package exceptions;

public class CurrencyException extends Exception {
    public CurrencyException() {
        super("Not recognized currency");
    }
}
