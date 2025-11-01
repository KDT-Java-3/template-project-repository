package com.sparta.commerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundProductException extends RuntimeException {

    private HttpStatus status;

    private String message = "Product not found.";

    public NotFoundProductException(String message) {
        super(message);
    }
}
