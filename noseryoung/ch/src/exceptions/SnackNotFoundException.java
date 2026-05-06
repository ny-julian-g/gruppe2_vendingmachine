package exceptions;

public class SnackNotFoundException extends RuntimeException {
    public SnackNotFoundException(String message) {
        super(message);
    }
}
