package challenge.leandrini.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message, final Object... args) {
        super(message);
    }
}
