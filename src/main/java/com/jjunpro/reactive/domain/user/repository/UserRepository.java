package com.jjunpro.reactive.domain.user.repository;

import com.jjunpro.reactive.domain.user.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends CrudRepository<User, String> {

    Mono<User> findByUsername(String username);

    Flux<User> savaAll(List<User> users);

    Mono<Void> deleteAll(List<User> users);
}
