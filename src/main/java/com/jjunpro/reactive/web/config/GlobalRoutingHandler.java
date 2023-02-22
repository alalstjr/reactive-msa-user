package com.jjunpro.reactive.web.config;

import com.jjunpro.reactive.web.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface GlobalRoutingHandler {

    static <T> Mono<ServerResponse> doRequest(Mono<T> action, HttpStatus httpStatus) {
        return action
            .flatMap(result -> ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(result))
            )
            .onErrorResume(error -> ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new ErrorDto(error.getMessage())))
            );
    }
}