package com.jaehyuk.week_01_project.exception.custom;

public class InvalidRefundRequestException extends RuntimeException {
    public InvalidRefundRequestException(String message) {
        super(message);
    }
}
