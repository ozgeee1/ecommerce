package com.ecommerce.usermanagement.controller;

import com.ecommerce.usermanagement.error.ApiError;
import com.ecommerce.usermanagement.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { BadRequestException.class})
    public ResponseEntity<ApiError> handleBadCredentialsException(Exception ex){
        ApiError build = ApiError.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value()).build();
        return ResponseEntity.ok(build);
    }



}