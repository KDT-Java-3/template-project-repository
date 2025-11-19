package io.depark.commerceservice.common;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final String VALIDATE_ERROR = "VALIDATE_ERROR";
    private final String SERVER_ERROR = "SERVER_ERROR";

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleResponseException(ServiceException ex) {
        return ApiResponse.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        AtomicReference<String> errors = new AtomicReference<>("");
        ex.getBindingResult().getAllErrors().forEach(c -> errors.set(c.getDefaultMessage()));

        return ApiResponse.badRequest(VALIDATE_ERROR, String.valueOf(errors));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindException(BindException ex) {
        AtomicReference<String> errors = new AtomicReference<>("");
        ex.getBindingResult().getAllErrors().forEach(c -> errors.set(c.getDefaultMessage()));

        return ApiResponse.badRequest(VALIDATE_ERROR, String.valueOf(errors));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException ex) {
        AtomicReference<String> errors = new AtomicReference<>("");

        String rootCauseMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        if (!isEmpty(rootCauseMessage) && (
                rootCauseMessage.contains("uk_product_name")
                || rootCauseMessage.contains("uk_category_name"))
        ) {
            return ApiResponse.conflict("DUPLICATE_NAME", "중복된 name 입니다.");
        }

        return ApiResponse.serverError(SERVER_ERROR, String.valueOf(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> serverException(Exception ex) {
        return ApiResponse.serverError(SERVER_ERROR, ex.getMessage());
    }

}