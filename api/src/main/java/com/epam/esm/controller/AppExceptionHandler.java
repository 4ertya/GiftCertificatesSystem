package com.epam.esm.controller;


import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ExceptionResponse;
import com.epam.esm.exception.ValidationExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
public class AppExceptionHandler {

    private final static String SQL_ERROR_MESSAGE = "sql_error";
    private final static String SQL_ERROR_CODE = "5001";
    private final static String TYPE_MISMATCH_CODE = "6001";


    private final MessageSource messageSource;

    @ExceptionHandler(NoHandlerFoundException.class)
    ResponseEntity<ExceptionResponse> handleNotFoundMapping(NoHandlerFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getLocalizedMessage(), "4000");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationExceptionResponse>> validationErrorHandler(MethodArgumentNotValidException e) {
        List<ValidationExceptionResponse> list = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> new ValidationExceptionResponse(x.getField(), x.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> EntityNotFoundExceptionHandler(EntityNotFoundException e, WebRequest webRequest) {
        String message = messageSource.getMessage(e.getErrorCode(), null, webRequest.getLocale());
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> EntityAlreadyExistExceptionHandler(EntityAlreadyExistException e, WebRequest webRequest) {
        String message = messageSource.getMessage(e.getErrorCode(), null, webRequest.getLocale());
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponse> sqlExceptionHandler(SQLException e, WebRequest webRequest) {
        log.error(e.getMessage(), e);
        String message = messageSource.getMessage(SQL_ERROR_MESSAGE, null, webRequest.getLocale());
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, SQL_ERROR_CODE);
        return new ResponseEntity<>(exceptionResponse, INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> typeMismatchHandle(MethodArgumentTypeMismatchException ex, WebRequest webRequest) {
        String parameterName = ex.getName();
        String enumValues = Arrays.stream(ex.getRequiredType().getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        String message = messageSource.getMessage(TYPE_MISMATCH_CODE, new Object[]{parameterName, enumValues}, webRequest.getLocale());

        return ResponseEntity.badRequest().body(new ExceptionResponse(message, TYPE_MISMATCH_CODE));
    }

    @ExceptionHandler
    public ResponseEntity<List<ValidationExceptionResponse>> constraintHandle(ConstraintViolationException ex) {
        List<ValidationExceptionResponse> list = new ArrayList<>();
        ex.getConstraintViolations().forEach(e -> {
                    ValidationExceptionResponse response = new ValidationExceptionResponse(e.getMessageTemplate(), ex.getLocalizedMessage());
                    list.add(response);
                }
        );
        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<ExceptionResponse> handleException(Throwable e) {
        log.error(e.getMessage(), e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), "5000");
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

}
