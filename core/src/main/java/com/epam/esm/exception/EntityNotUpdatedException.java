package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class EntityNotUpdatedException extends ServiceException {

    private final String errorCode = "4003";
    private final Long id;


    public EntityNotUpdatedException(String message, long id) {
        super(message);
        this.id = id;
    }

}
