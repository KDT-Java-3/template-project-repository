package com.example.demo.common.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map<String, Object> errorInfo;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.message);
        this.errorCode = errorCode;
        this.errorInfo = null;
    }

    public CommonException(ErrorCode errorCode, Map<String, Object> errorInfo) {
        super(errorCode.message);
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

}
