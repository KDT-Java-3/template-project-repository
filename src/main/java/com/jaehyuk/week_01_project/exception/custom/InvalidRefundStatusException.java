package com.jaehyuk.week_01_project.exception.custom;

public class InvalidRefundStatusException extends RuntimeException {
    public InvalidRefundStatusException(String message) {
        super(message);
    }
}
