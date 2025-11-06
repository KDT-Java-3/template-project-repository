package com.sparta.demo.exception;

import com.sparta.demo.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 전역 예외 처리기
 * 모든 Controller에서 발생하는 예외를 중앙에서 처리
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ServiceException 처리
     * 비즈니스 로직에서 발생하는 커스텀 예외
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse<Void>> handleServiceException(ServiceException e) {
        log.error("ServiceException: code={}, message={}", e.getExceptionCode().getCode(), e.getMessage());

        return ResponseEntity
                .status(e.getExceptionCode().getHttpStatus())
                .body(ApiResponse.<Void>builder()
                        .result(false)
                        .error(ApiResponse.Error.of(
                                e.getExceptionCode().getCode(),
                                e.getMessage()
                        ))
                        .build());
    }

    /**
     * Validation 예외 처리
     * @Valid 어노테이션으로 검증 실패 시 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException e) {
        log.error("Validation error: {}", e.getMessage());

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.<Map<String, String>>builder()
                        .result(false)
                        .error(ApiResponse.Error.of(
                                ServiceExceptionCode.INVALID_INPUT_VALUE.getCode(),
                                ServiceExceptionCode.INVALID_INPUT_VALUE.getMessage()
                        ))
                        .data(errors)
                        .build());
    }

    /**
     * 필수 파라미터 누락 예외 처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingParameterException(MissingServletRequestParameterException e) {
        log.error("Missing parameter: {}", e.getParameterName());

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.<Void>builder()
                        .result(false)
                        .error(ApiResponse.Error.of(
                                ServiceExceptionCode.MISSING_REQUIRED_PARAMETER.getCode(),
                                ServiceExceptionCode.MISSING_REQUIRED_PARAMETER.getMessage() + ": " + e.getParameterName()
                        ))
                        .build());
    }

    /**
     * IllegalArgumentException 처리
     * 잘못된 인자 전달 시 발생
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Illegal argument: {}", e.getMessage());

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.<Void>builder()
                        .result(false)
                        .error(ApiResponse.Error.of(
                                ServiceExceptionCode.INVALID_INPUT_VALUE.getCode(),
                                e.getMessage()
                        ))
                        .build());
    }

    /**
     * IllegalStateException 처리
     * 잘못된 상태에서 메서드 호출 시 발생
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalStateException(IllegalStateException e) {
        log.error("Illegal state: {}", e.getMessage());

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.<Void>builder()
                        .result(false)
                        .error(ApiResponse.Error.of(
                                ServiceExceptionCode.INVALID_ORDER_STATUS.getCode(),
                                e.getMessage()
                        ))
                        .build());
    }

    /**
     * 예상하지 못한 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unexpected error occurred", e);

        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.<Void>builder()
                        .result(false)
                        .error(ApiResponse.Error.of(
                                ServiceExceptionCode.INTERNAL_SERVER_ERROR.getCode(),
                                ServiceExceptionCode.INTERNAL_SERVER_ERROR.getMessage()
                        ))
                        .build());
    }
}
