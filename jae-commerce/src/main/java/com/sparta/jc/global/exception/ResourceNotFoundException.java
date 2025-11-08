package com.sparta.jc.global.exception;

/**
 * 리소스 못찾을 때 예외
 */
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
