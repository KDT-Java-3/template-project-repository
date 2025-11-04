package com.jaehyuk.week_01_project.exception.custom;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
