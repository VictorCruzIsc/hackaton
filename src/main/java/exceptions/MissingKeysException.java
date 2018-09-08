package exceptions;

public class MissingKeysException extends Exception {
    public MissingKeysException() {
        super("BitsoClient does not have api keys or are empty, private calls are not available");
    }
}
