package com.ecommerce.usermanagement.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
