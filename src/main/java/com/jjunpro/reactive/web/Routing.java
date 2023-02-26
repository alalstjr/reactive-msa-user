package com.jjunpro.reactive.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

import com.jjunpro.reactive.web.handler.TeamHandlers;
import com.jjunpro.reactive.web.handler.UserHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Routing {

    @Bean
    public RouterFunction<ServerResponse> routingFunction(TeamHandlers teamHandlers, UserHandlers userHandlers) {
        return RouterFunctions
            .nest(
                path("/users"),
                RouterFunctions
                    .route(GET("").and(accept(MediaType.APPLICATION_JSON)), userHandlers::findAllUsers)
                    .andRoute(GET("/id/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandlers::findById)
                    .andRoute(GET("/{username}").and(accept(MediaType.APPLICATION_JSON)), userHandlers::findByUsername)
                    .andRoute(POST("").and(accept(MediaType.APPLICATION_JSON)), userHandlers::createUser)
                    .andRoute(DELETE("/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandlers::deleteUser)
            )
            .andNest(
                path("/teams"),
                RouterFunctions
                    .route(GET("").and(accept(MediaType.APPLICATION_JSON)), teamHandlers::findAllTeams)
                    .andRoute(GET("/{name}").and(accept(MediaType.APPLICATION_JSON)), teamHandlers::findByName)
                    .andRoute(GET("/id/{id}").and(accept(MediaType.APPLICATION_JSON)), teamHandlers::findById)
                    .andRoute(POST("").and(accept(MediaType.APPLICATION_JSON)), teamHandlers::addTeam)
                    .andRoute(DELETE("/{id}").and(accept(MediaType.APPLICATION_JSON)), teamHandlers::deleteTeam)
            )
            .andNest(
                path("/login"),
                RouterFunctions
                    .route(POST("").and(accept(MediaType.APPLICATION_JSON)), userHandlers::login)
            );
    }
}