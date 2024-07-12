package com.example.ncc_spring.exception;

import com.example.ncc_spring.exception.ExceptionEntity.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Object> internalException(Exception e){
        ErrorResponse errorResponse = new ErrorResponse("Internal server error", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("Bad request", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(Exception e){
        ErrorResponse errorResponse = new ErrorResponse("Not found", e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<Object> InvalidException(Exception e){
        ErrorResponse errorResponse = new ErrorResponse("Invalid exception", e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> UnauthorizedException(Exception e){
        ErrorResponse errorResponse = new ErrorResponse("Unauthorized", e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }
}
