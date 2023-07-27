package com.ecommerce.usermanagement.controller;

import com.ecommerce.usermanagement.error.ApiError;
import com.ecommerce.usermanagement.exception.BadRequestException;
import com.ecommerce.usermanagement.exception.UserNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { UserNotFoundException.class})
    public ResponseEntity<ApiError> handleUserNotFound(Exception ex){
        ApiError build = ApiError.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value()).build();
        return ResponseEntity.ok(build);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> errors = new ArrayList<>();
        constraintViolations.forEach(constraintViolation -> errors.add(constraintViolation.getPropertyPath()+" "+constraintViolation.getMessage()));
        ApiError build = ApiError.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value()).errors(errors).build();
        return ResponseEntity.ok(build);
    }



}
