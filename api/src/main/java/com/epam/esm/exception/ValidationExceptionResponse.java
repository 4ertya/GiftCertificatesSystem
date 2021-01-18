package com.epam.esm.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"field", "message", "errorCode"})
public class ValidationExceptionResponse {

    private String field;
    private String message;


}
