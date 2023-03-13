package com.jjunpro.reactive.infrastructure.persistence.repository;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.UserUtils;
import com.jjunpro.reactive.domain.user.dto.GetUserDto;
import com.jjunpro.reactive.infrastructure.persistence.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserCacheRepositoryImpl {

    private final ReactiveRedisComponent reactiveRedisComponent;
    private static final String USERS_KEY = "USERS";

    public Mono<User> save(User user) {
        var userId = UserUtils.toId.apply(user);
        return reactiveRedisComponent.set(USERS_KEY, userId, user.toGetUserDto()).map(u -> user);
    }

    public Mono<User> findById(String id) {
        return reactiveRedisComponent
            .get(USERS_KEY, id)
            .flatMap(
                userDto -> {
                    GetUserDto data = ObjectMapperUtils.objectMapper(userDto, GetUserDto.class);
                    return Mono.just(data.toUser());
                }
            )
            ;
    }
}
