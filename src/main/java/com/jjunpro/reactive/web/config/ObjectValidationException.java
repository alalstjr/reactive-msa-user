package com.jjunpro.reactive.web.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class ObjectValidationException extends RuntimeException {

    public ObjectValidationException(String errorDetails) {
        super("Please supply the valid data: " + errorDetails);
    }
}