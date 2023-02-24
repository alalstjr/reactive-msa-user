package com.jjunpro.reactive.infrastructure.persistence.dao;

import com.jjunpro.reactive.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserDao extends ReactiveMongoRepository<UserEntity, String> {

    Flux<UserEntity> findByTeamId(String teamId);

    Mono<UserEntity> findByUsername(String username);

    Mono<UserEntity> findByNickname(String email);
}