package com.jjunpro.reactive.application.exception;

/**
 * 사용자 서비스 익셉션
 *
 * @author jjunpro
 * @since 2023/02/22 PM 9:55
 */
public class UserServiceException extends RuntimeException {

    public UserServiceException(String message) {
        super(message);
    }
}
