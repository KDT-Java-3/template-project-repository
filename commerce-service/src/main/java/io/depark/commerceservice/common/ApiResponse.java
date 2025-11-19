package io.depark.commerceservice.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    Boolean result;
    Error error;
    T data;

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

    public static <T> ResponseEntity<ApiResponse<T>> error(String code, String message) {
        return ResponseEntity.badRequest().body(
                ApiResponse.<T>builder()
                        .result(false)
                        .error(Error.of(code, message))
                        .build()
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> conflict(String code, String errorMessage) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.<T>builder()
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

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(success(data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created() {
        return ResponseEntity.status(HttpStatus.CREATED).body(success());
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Error(String code, String message) {
        public static Error of(String code, String message) {
            return new Error(code, message);
        }
    }
}
