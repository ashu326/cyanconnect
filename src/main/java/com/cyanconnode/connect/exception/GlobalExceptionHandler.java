package com.cyanconnode.connect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<List<String>> handleConflict(ConflictException ex)
    {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(List.of(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<List<String>> handleRuntime(RuntimeException ex)
    {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(List.of("Internal server error"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<List<String>> handleInvalidJson(HttpMessageNotReadableException ex)
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(List.of("Invalid request payload"));
    }
}
