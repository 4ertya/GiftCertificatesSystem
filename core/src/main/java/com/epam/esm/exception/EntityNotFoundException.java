package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends ServiceException {
    private final String errorCode = "4001";

    public EntityNotFoundException(String message) {
        super(message);
    }
}
