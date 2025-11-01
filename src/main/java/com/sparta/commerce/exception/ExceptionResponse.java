package com.sparta.commerce.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponse(
        HttpStatus status,
        String message,
        LocalDateTime timestamp
) {

    public static ExceptionResponse of(HttpStatus status, String message) {
        return new ExceptionResponse(
                status,
                message,
                LocalDateTime.now()
        );
    }
}
