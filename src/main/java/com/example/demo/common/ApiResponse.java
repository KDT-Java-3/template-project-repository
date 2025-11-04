package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    Boolean result; // success
    Error error;
    T data;

    // 성공응답 - 데이터 없을 경우
    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .result(true)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .result(true)
                .data(data)
                .build();
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String code, String errorMessage) {
        return ResponseEntity.ok(ApiResponse.<T>builder()
                .result(false)
                .error(Error.of(code, errorMessage))
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String code, String errorMessage) {
        return ResponseEntity.badRequest().body(ApiResponse.<T>builder()
                .result(false)
                .error(Error.of(code, errorMessage))
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> serverError(String code, String errorMessage) {
        return ResponseEntity.status(500).body(ApiResponse.<T>builder()
                .result(false)
                .error(Error.of(code, errorMessage))
                .build());
    }

    public record Error(String code, String message) {
        public static Error of(String code, String message) {
            return new Error(code, message);
        }
    }

}
