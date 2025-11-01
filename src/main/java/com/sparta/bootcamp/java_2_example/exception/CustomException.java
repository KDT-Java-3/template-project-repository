package com.sparta.bootcamp.java_2_example.exception;


import com.sparta.bootcamp.java_2_example.common.enums.ErrorCode;

public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String customMessage;

    // ErrorCode만 사용할 때 기본 메시지 사용
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.customMessage = null;
    }

    // ErrorCode + customMessage
    public CustomException(ErrorCode errorCode, String customMessage) {
        super(customMessage != null ? customMessage : errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}
