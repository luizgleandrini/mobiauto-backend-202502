package challenge.leandrini.exception;

import challenge.leandrini.common.HttpResponseStatus;
import challenge.leandrini.common.WebRequest;
import challenge.leandrini.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(1)
public class MobiAutoExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(MobiAutoExceptionHandler.class);

    @Autowired
    private WebRequest webRequest;

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public HttpResponseStatus handleNotFoundException(final NotFoundException exception) {
        log.warn("NotFoundException: {}", exception.getMessage());
        return buildHttpResponseStatus(HttpStatus.NOT_FOUND, exception.getMessage(), webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UniqueConstraintException.class)
    public HttpResponseStatus handleUniqueConstraintException(final UniqueConstraintException exception) {
        log.warn("UniqueConstraintException: {}", exception.getMessage());
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, exception.getMessage(), webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public HttpResponseStatus handleAccessDeniedException(final AccessDeniedException exception) {
        log.warn("AccessDeniedException: {}", exception.getMessage());
        return buildHttpResponseStatus(HttpStatus.FORBIDDEN, exception.getMessage(), webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpResponseStatus handleValidationException(final MethodArgumentNotValidException exception) {
        String details = exception.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));
        if (details.isBlank()) details = "Validation error.";
        
        log.warn("ValidationException: {}", details);
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, details, webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public HttpResponseStatus handleIllegalArgumentException(final IllegalArgumentException exception) {
        log.warn("IllegalArgumentException: {}", exception.getMessage());
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, exception.getMessage(), webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public HttpResponseStatus handleBusinessException(final BusinessException exception) {
        log.info("BusinessException: {}, Args: {}", exception.getMessage(), exception.getArgs());
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, exception.getMessage(), webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserNotAllowedException.class)
    public HttpResponseStatus handleUserNotAllowedException(final UserNotAllowedException exception) {
        log.warn("UserNotAllowedException: {}, Args: {}", exception.getMessage(), exception.getArgs());
        return buildHttpResponseStatus(HttpStatus.FORBIDDEN, exception.getMessage(), webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public HttpResponseStatus handleCustomValidationException(final ValidationException exception) {
        log.warn("ValidationException: {}, Args: {}", exception.getMessage(), exception.getArgs());
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, exception.getMessage(), webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public HttpResponseStatus handleAuthenticationException(final AuthenticationException exception) {
        log.warn("AuthenticationException: {}", exception.getMessage());
        return buildHttpResponseStatus(HttpStatus.UNAUTHORIZED, "Authentication required", webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public HttpResponseStatus handleTypeMismatchException(final MethodArgumentTypeMismatchException exception) {
        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s", 
            exception.getValue(), 
            exception.getName(), 
            exception.getRequiredType() != null ? exception.getRequiredType().getSimpleName() : "unknown");
        
        log.warn("MethodArgumentTypeMismatchException: {}", message);
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, message, webRequest.getPath());
    }

    private static HttpResponseStatus buildHttpResponseStatus(final HttpStatus httpStatus, final String message, final String path) {
        return new HttpResponseStatus(LocalDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), message, path);
    }

}
