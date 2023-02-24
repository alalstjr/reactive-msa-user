package com.jjunpro.reactive.web.error;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectValidator {

    private final Validator validator;

    public <T> T validate(T object) {
        var errors = validator.validate(object);
        if (errors.isEmpty()) {
            return object;
        } else {
            String errorDetails = errors
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

            throw new ObjectValidationException(errorDetails);
        }
    }
}