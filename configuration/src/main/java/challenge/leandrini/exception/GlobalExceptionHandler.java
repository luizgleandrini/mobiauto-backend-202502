package challenge.leandrini.exception;

import challenge.leandrini.common.HttpResponseStatus;
import challenge.leandrini.common.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
@Order(2)
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private WebRequest webRequest;

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public HttpResponseStatus handleException(final Exception exception) {
        log.error("Unhandled exception: {}", exception.getMessage(), exception);
        log.error("Exception type: {}", exception.getClass().getSimpleName());
        log.error("StackTrace: ", exception);
        
        return buildHttpResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error. Please try again later.", webRequest.getPath());
    }

    private static HttpResponseStatus buildHttpResponseStatus(final HttpStatus httpStatus, final String message, final String path) {
        return new HttpResponseStatus(LocalDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), message, path);
    }
}
