package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class EntityNotUpdatedException extends ServiceException {

    private final String errorCode = "4003";
    private final Integer id;


    public EntityNotUpdatedException(String message, int id) {
        super(message);
        this.id = id;
    }

}
