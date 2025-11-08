package com.sparta.jc.global.exception;

/**
 * 글로벌 기본 예외
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

}