package com.cyanconnode.connect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<List<String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex)
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(List.of("Invalid parameter type: " + ex.getName()));
    }
}
