package com.jjunpro.reactive.domain.config.repository;

import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudRepository<T, O> {

    Flux<T> findAll();
    Flux<T> findAllById(List<O> ids);
    Mono<T> findById(O id);
    Mono<T> save(T t);
    Mono<T> delete(O id);
}
