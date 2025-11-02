package com.sparta.commerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundProductException extends RuntimeException {

    private HttpStatus status;

    private String message;

    public NotFoundProductException() {
        super("상품을 찾을 수 없습니다.");
        this.status = HttpStatus.NOT_FOUND;
    }

    public NotFoundProductException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
}
