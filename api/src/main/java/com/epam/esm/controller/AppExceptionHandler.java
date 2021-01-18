package com.epam.esm.controller;


import com.epam.esm.exception.EntityNotAddedException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ExceptionResponse;
import com.epam.esm.exception.ValidationExceptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@AllArgsConstructor
public class AppExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationExceptionResponse>> validationErrorHandler(MethodArgumentNotValidException e, WebRequest request){
        List<ValidationExceptionResponse> list = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x ->new ValidationExceptionResponse(x.getField(), x.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> EntityNotFoundHandler(EntityNotFoundException e, WebRequest webRequest ) {
        System.out.println(webRequest.getLocale());
        String message = messageSource.getMessage(e.getErrorCode(),null, webRequest.getLocale());
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage()+" "+ message, e.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotAddedException.class)
    public ResponseEntity<ExceptionResponse> EntityNotAddedHandler(EntityNotAddedException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), e.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
