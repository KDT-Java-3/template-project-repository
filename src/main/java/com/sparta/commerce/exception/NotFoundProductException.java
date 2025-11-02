package com.sparta.commerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundProductException extends RuntimeException {

    private HttpStatus status;

    private String message;

    public NotFoundProductException() {
        super();
        this.message = "존재하지 않는 상품입니다.";
        this.status = HttpStatus.NOT_FOUND;
    }

    public NotFoundProductException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
}
