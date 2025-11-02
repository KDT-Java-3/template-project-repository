package com.sparta.commerce.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage(),  e);

        ExceptionResponse errorResponse = ExceptionResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> eethodArgumentNotValidException(
            final MethodArgumentNotValidException e
    ) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage());

        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ExceptionResponse errorResponse = ExceptionResponse.of(
                HttpStatus.BAD_REQUEST,
                message
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundProductException(
            final NotFoundProductException e
    ) {
        return commonCustomResponseEntity(
                e.getStatus(),
                e.getMessage()
        );
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundUserException(
            final NotFoundUserException e
    ) {
        return commonCustomResponseEntity(
                e.getStatus(),
                e.getMessage()
        );
    }

    private ResponseEntity<ExceptionResponse> commonCustomResponseEntity(
            final HttpStatus status,
            final String message
    ) {
        ExceptionResponse errorResponse = ExceptionResponse.of(
                status,
                message
        );

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

}
