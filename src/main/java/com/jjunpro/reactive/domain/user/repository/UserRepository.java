package com.jjunpro.reactive.domain.user.repository;

import com.jjunpro.reactive.domain.configs.repository.CrudRepository;
import com.jjunpro.reactive.domain.user.User;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends CrudRepository<User, String> {

    Flux<User> findByTeamId(String teamId);
    Mono<User> findByUsername(String username);
    Flux<User> saveAll(List<User> users);
    Mono<Void> deleteAll(List<User> users);
}
