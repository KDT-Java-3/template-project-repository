package com.sparta.jc.global.exception.handler;

import com.sparta.jc.global.dto.ApiResponse;
import com.sparta.jc.global.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 공통) 예외_처리_핸들러
 * TODO 각 메서드에 주석 달기.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 클라이언트(유효성) 에러
    private static final String VALIDATE_ERROR = "VALIDATE_ERROR";
    // 서버 에러
    private static final String SERVER_ERROR = "SERVER_ERROR";

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleResponseException(ServiceException ex) {
        return ApiResponse.error(ex.getErrorCode().getCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = extractErrors(ex.getBindingResult());
        return ApiResponse.badRequest(VALIDATE_ERROR, buildValidationSummary(errors.size()), errors);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindException(BindException ex) {
        List<String> errors = extractErrors(ex.getBindingResult());
        return ApiResponse.badRequest(VALIDATE_ERROR, buildValidationSummary(errors.size()), errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> serverException(Exception ex) {
        return ApiResponse.serverError(SERVER_ERROR, ex.getMessage());
    }

    private List<String> extractErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList());
    }

    private String buildValidationSummary(int errorCount) {
        return errorCount > 1
                ? "요청 값에 " + errorCount + "개의 오류가 있습니다."
                : "요청 값이 올바르지 않습니다.";
    }

}
