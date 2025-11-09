package com.jaehyuk.week_01_project.exception.custom;

public class RefundNotFoundException extends RuntimeException {
    public RefundNotFoundException(String message) {
        super(message);
    }
}
