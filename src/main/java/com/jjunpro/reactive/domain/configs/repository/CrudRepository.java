package com.jjunpro.reactive.domain.configs.repository;

import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudRepository<T, ID> {

    Flux<T> findAll();
    Flux<T> findAllById(List<ID> ids);
    Mono<T> findById(ID id);
    Mono<T> save(T t);
    Mono<T> delete(ID id);
}
