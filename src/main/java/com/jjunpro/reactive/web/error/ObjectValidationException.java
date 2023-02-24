package com.jjunpro.reactive.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ObjectValidationException extends ResponseStatusException {

    public ObjectValidationException(String errorDetails) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, errorDetails);
    }
}