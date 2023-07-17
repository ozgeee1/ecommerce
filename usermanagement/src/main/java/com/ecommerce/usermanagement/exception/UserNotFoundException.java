package com.ecommerce.usermanagement.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
