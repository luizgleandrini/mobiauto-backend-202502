package challenge.leandrini.exceptions;

public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException(String message, final Object... args) {
        super(message);
    }
}
