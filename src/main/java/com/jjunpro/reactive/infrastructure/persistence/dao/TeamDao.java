package com.jjunpro.reactive.infrastructure.persistence.dao;

import com.jjunpro.reactive.infrastructure.persistence.entity.TeamEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TeamDao extends ReactiveMongoRepository<TeamEntity, String> {
    Mono<TeamEntity> findByName(String name);
}
