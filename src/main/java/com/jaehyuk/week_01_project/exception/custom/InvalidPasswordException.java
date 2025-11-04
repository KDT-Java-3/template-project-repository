package com.jaehyuk.week_01_project.exception.custom;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
