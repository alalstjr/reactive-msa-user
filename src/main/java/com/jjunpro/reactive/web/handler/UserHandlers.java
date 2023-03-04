package com.jjunpro.reactive.web.handler;

import com.jjunpro.reactive.application.service.UserService;
import com.jjunpro.reactive.domain.user.dto.CreateUserDto;
import com.jjunpro.reactive.domain.user.dto.GetUserDto;
import com.jjunpro.reactive.domain.user.dto.LoginUserDto;
import com.jjunpro.reactive.web.config.GlobalRoutingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandlers {

    private final UserService     userService;

    public Mono<ServerResponse> findAllUsers(ServerRequest serverRequest) {
        serverRequest.headers();
        return ServerResponse
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userService.findAllUsers(), GetUserDto.class);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        var userId = serverRequest.pathVariable("id");
        return GlobalRoutingHandler.doRequest(userService.findById(userId), HttpStatus.OK);
    }

    public Mono<ServerResponse> findByUsername(ServerRequest serverRequest) {
        var username = serverRequest.pathVariable("username");
        return GlobalRoutingHandler.doRequest(userService.findByUsername(username), HttpStatus.OK);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        var createUserDtoMono = serverRequest.bodyToMono(CreateUserDto.class);
        return GlobalRoutingHandler.doRequest(userService.addUser(createUserDtoMono), HttpStatus.CREATED);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        var userId = serverRequest.pathVariable("id");
        return GlobalRoutingHandler.doRequest(userService.deleteUser(userId), HttpStatus.OK);
    }

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        var loginUserDtoMono = serverRequest.bodyToMono(LoginUserDto.class);
        return GlobalRoutingHandler.doRequest(userService.login(loginUserDtoMono), HttpStatus.CREATED);
    }
}