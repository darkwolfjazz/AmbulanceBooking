package com.ambulancebooking.project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(MethodArgumentNotValidException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ResponseEntity<Map<String,String>>handleValidationExceptions(MethodArgumentNotValidException exception){
    Map<String,String>errorMessage= new HashMap<>();
    for(FieldError error:exception.getBindingResult().getFieldErrors()){
        errorMessage.put(error.getField(),error.getDefaultMessage());
    }
    return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
}
}
