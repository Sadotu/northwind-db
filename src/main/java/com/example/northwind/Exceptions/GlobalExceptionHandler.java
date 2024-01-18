package com.example.northwind.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String error = ex.getMessage();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404 Not Found
                .body(error);
    }

    @ExceptionHandler(CannotDeleteException.class)
    public ResponseEntity<Object> handleCannotDeleteException(CannotDeleteException ex) {
        String error = ex.getMessage();
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409 Conflict
                .body(error);
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmployeeAlreadyExistsException(EmployeeAlreadyExistsException ex) {
        String error = ex.getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400 Bad Request
                .body(error);
    }
}
