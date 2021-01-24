package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final String errorCode = "4001";
}
