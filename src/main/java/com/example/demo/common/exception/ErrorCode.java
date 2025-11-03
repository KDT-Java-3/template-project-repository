package com.example.demo.common.exception;

public enum ErrorCode {
    NOT_FOUND(404, "E404", "Not Found"),
    INTERNAL_ERROR(500, "E500", "Internal Server Error"),
    ;

    public final int status;
    public final String code;
    public final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}