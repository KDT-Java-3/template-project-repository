package com.sparta.commerce.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("Exception: {}",e.getMessage(),  e);

        ExceptionResponse errorResponse = ExceptionResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "서버 내부 오류가 발생했습니다."
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundProductException(
            final NotFoundProductException e
    ) {

        ExceptionResponse errorResponse = ExceptionResponse.of(
                e.getStatus(),
                e.getMessage()
        );

        return ResponseEntity
                .status(e.getStatus())
                .body(errorResponse);
    }

}
