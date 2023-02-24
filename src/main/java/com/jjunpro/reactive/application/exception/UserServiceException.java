package com.jjunpro.reactive.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * 사용자 서비스 익셉션
 *
 * @author jjunpro
 * @since 2023/02/22 PM 9:55
 */
public class UserServiceException extends ResponseStatusException {

    public UserServiceException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
