package challenge.leandrini.exceptions;

public class ValidationException extends RuntimeException {
    
    private final Object[] args;
    
    public ValidationException(String message, Object... args) {
        super(message);
        this.args = args;
    }
    
    public Object[] getArgs() {
        return args;
    }
}