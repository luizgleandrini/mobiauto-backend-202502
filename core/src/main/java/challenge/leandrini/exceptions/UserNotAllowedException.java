package challenge.leandrini.exceptions;

public class UserNotAllowedException extends RuntimeException {
    
    private final Object[] args;
    
    public UserNotAllowedException(String message, Object... args) {
        super(message);
        this.args = args;
    }
    
    public Object[] getArgs() {
        return args;
    }
}