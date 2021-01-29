package com.epam.esm.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"message","errorCode"})
public class ExceptionResponse {
    private String message;
    private String errorCode;
}
