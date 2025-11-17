package com.example.demo.global.exception;

import com.example.demo.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final String VALIDATE_ERROR = "VALIDATE_ERROR";
    private final String DUPLICATE_ERROR = "DUPLICATE_ERROR";
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
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "데이터 무결성 제약 조건 위반입니다.";

        // 중복 키 오류 메시지 추출
        if (ex.getMessage() != null && ex.getMessage().contains("uk_product_name")) {
            message = "이미 존재하는 상품명입니다.";
        } else if (ex.getMessage() != null && ex.getMessage().contains("Duplicate entry")) {
            message = "중복된 데이터입니다.";
        }

        return ApiResponse.error(DUPLICATE_ERROR, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> serverException(Exception ex) {
        return ApiResponse.serverError(SERVER_ERROR, ex.getMessage());
    }
}
