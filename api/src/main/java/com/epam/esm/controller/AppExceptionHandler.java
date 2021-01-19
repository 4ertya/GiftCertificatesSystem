package com.epam.esm.controller;


import com.epam.esm.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@AllArgsConstructor
public class AppExceptionHandler {

    private final static String SQL_ERROR_MESSAGE = "sql_error";
    private final static String SQL_ERROR_CODE = "5001";

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationExceptionResponse>> validationErrorHandler(MethodArgumentNotValidException e) {
        List<ValidationExceptionResponse> list = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> new ValidationExceptionResponse(x.getField(), x.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionResponse> serviceExceptionHandler(ServiceException e, WebRequest webRequest) {
        String message = messageSource.getMessage(e.getErrorCode(), null, webRequest.getLocale());
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, e instanceof EntityNotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponse> sqlExceptionHandler(SQLException e, WebRequest webRequest) {
        String message = messageSource.getMessage(SQL_ERROR_MESSAGE, null, webRequest.getLocale());
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, SQL_ERROR_CODE);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
