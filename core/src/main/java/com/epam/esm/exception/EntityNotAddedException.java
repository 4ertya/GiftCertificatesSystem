package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class EntityNotAddedException extends ServiceException {
    private final String errorCode = "4002";

    public EntityNotAddedException(String message) {
        super(message);
    }
}
