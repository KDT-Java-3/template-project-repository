package com.sparta.bootcamp.java_2_example.exception;


import com.sparta.bootcamp.java_2_example.common.enums.ErrorCode;
import com.sparta.bootcamp.java_2_example.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {

        ErrorCode code = ex.getErrorCode();

        String message = (ex.getCustomMessage() != null)
                ? ex.getCustomMessage()
                : code.getDefaultMessage();

        ErrorResponse response = ErrorResponse.builder()
                .status(code.getStatus().value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(code.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse response = ErrorResponse.builder()
                .status(ErrorCode.BAD_REQUEST.getStatus().value())
                .message("입력값 검증 실패")
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(ErrorCode.BAD_REQUEST.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        ErrorCode code = ErrorCode.INTERNAL_SERVER_ERROR;

        ErrorResponse response = ErrorResponse.builder()
                .status(code.getStatus().value())
                .message(code.getDefaultMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(code.getStatus()).body(response);
    }
}
