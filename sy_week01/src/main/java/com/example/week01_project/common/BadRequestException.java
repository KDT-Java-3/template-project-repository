package com.example.week01_project.common;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}