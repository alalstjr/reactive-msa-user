package com.jjunpro.reactive.web.error;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 유효성 검사 오브젝트
 *
 * @author jjunpro
 * @since 2023/02/24 PM 5:14
 */
@Component
@RequiredArgsConstructor
public class ObjectValidator {

    private final Validator validator;

    public <T> T validate(T object) {
        var errors = validator.validate(object);
        if (errors.isEmpty()) {
            return object;
        } else {
            StringBuilder errorDetails = new StringBuilder();
            errorDetails.append("{");

            int i = 1;
            for(ConstraintViolation<T> c : errors) {
                errorDetails.append("'").append(c.getPropertyPath()).append("'").append(":");
                errorDetails.append("'").append(c.getMessage()).append("'");

                /* 마지막이 아니라면 콤마를 찍어줍니다. */
                if(errors.size() != i) {
                    errorDetails.append(",");
                }
                i++;
            }
            errorDetails.append("}");

            throw new ObjectValidationException(errorDetails.toString());
        }
    }
}