package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class EntityNotDeletedException extends ServiceException {

    private final String errorCode = "4004";
    private final Integer id;

    public EntityNotDeletedException(String message, int id) {
        super(message);
        this.id = id;
    }
}
