package com.sprata.sparta_ecommerce.dto;

import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private final boolean success;
    private final T data;
    private final String message;

    public ResponseDto(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }
}