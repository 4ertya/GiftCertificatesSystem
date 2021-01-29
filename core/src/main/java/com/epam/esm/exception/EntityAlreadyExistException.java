package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistException extends RuntimeException{
    private final String errorCode = "5005";
}
