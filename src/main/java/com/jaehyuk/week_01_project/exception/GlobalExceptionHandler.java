package com.jaehyuk.week_01_project.exception;

import com.jaehyuk.week_01_project.exception.custom.CategoryNotFoundException;
import com.jaehyuk.week_01_project.exception.custom.DuplicateEmailException;
import com.jaehyuk.week_01_project.exception.custom.InvalidPasswordException;
import com.jaehyuk.week_01_project.exception.custom.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateEmailException(
            DuplicateEmailException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPasswordException(
            InvalidPasswordException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(
            CategoryNotFoundException ex
    ) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}