package com.example.demo.common.exception;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ApiResponse<Void>> handleEtc(CommonException ex) {
        ErrorCode ec = ErrorCode.INTERNAL_ERROR;
        ErrorResponse body = ErrorResponse.builder()
                .errorCode(ec.code)
                .errorMsg(ec.message)
                .errorInfo(ex.getErrorInfo())
                .build();
        return ResponseEntity.status(ec.status).body(ApiResponse.fail(body));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleEtc(Exception ex) {
        ErrorCode ec = ErrorCode.INTERNAL_ERROR;
        ErrorResponse body = ErrorResponse.builder()
                .errorCode(ec.code)
                .errorMsg(ec.message)
                .errorInfo(null)
                .build();
        return ResponseEntity.status(ec.status).body(ApiResponse.fail(body));
    }

}