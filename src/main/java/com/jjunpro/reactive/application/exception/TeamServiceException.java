package com.jjunpro.reactive.application.exception;

/**
 * 팀 서비스 익셉션
 *
 * @author jjunpro
 * @since 2023/02/22 PM 9:55
 */
public class TeamServiceException extends RuntimeException {

    public TeamServiceException(String message) {
        super(message);
    }
}