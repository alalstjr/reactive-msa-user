package com.jjunpro.reactive.domain.team.repository;

import com.jjunpro.reactive.domain.configs.repository.CrudRepository;
import com.jjunpro.reactive.domain.team.Team;
import reactor.core.publisher.Mono;

public interface TeamRepository extends CrudRepository<Team, String> {

    Mono<Team> findByName(String name);
}