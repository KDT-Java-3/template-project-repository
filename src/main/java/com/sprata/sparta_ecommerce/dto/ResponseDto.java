package com.sprata.sparta_ecommerce.dto;

import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private final boolean success;
    private final T data;
    private final String message;

    private ResponseDto(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> ResponseDto<T> success(T data, String message) {
        return new ResponseDto<>(true, data, message);
    }

    // 실패 응답
    public static <T> ResponseDto<T> fail(String message) {
        return new ResponseDto<>(false, null, message);
    }
}