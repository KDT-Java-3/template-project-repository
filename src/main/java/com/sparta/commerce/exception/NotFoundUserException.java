package com.sparta.commerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundUserException extends RuntimeException {

    private HttpStatus status;

    private String message;

    public NotFoundUserException() {
        super();
        this.message = "존재하지 않는 유저입니다.";
        this.status = HttpStatus.NOT_FOUND;
    }

    public NotFoundUserException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
}
