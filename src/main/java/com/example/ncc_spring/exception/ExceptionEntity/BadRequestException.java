package com.example.ncc_spring.exception.ExceptionEntity;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
    public BadRequestException(String message, Throwable throwable){
        super(message,throwable);
    }
}
