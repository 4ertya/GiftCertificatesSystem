package com.epam.esm.controller;


import com.epam.esm.exception.EntityNotAddedException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ExceptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@AllArgsConstructor
public class AppExceptionHandler {

    private final MessageSource messageSource;

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
