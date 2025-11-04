package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean result;
    private T data;
    private Error error;

    @Getter
    @AllArgsConstructor
    public static class Error {
        private String code;
        private String message;
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        ApiResponse<T> response = new ApiResponse<>(true, data, null);
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success() {
        ApiResponse<T> response = new ApiResponse<>(true, null, null);
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String code, String message) {
        ApiResponse<T> response = new ApiResponse<>(false, null, new Error(code, message));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String code, String message, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>(false, null, new Error(code, message));
        return ResponseEntity.status(status).body(response);
    }
}

