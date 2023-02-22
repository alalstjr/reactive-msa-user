package com.jjunpro.reactive.infrastructure.persistence.repository;

import com.jjunpro.reactive.domain.team.Team;
import com.jjunpro.reactive.domain.team.repository.TeamRepository;
import com.jjunpro.reactive.infrastructure.persistence.dao.TeamDao;
import com.jjunpro.reactive.infrastructure.persistence.exception.PersistenceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class TeamsRepositoryImpl implements TeamRepository {

    private final TeamDao teamDao;

    @Override
    public Flux<Team> findAll() {
        return teamDao.findAll()
                      .flatMap(team -> Mono.just(team.toTeam()));
    }

    @Override
    public Flux<Team> findAllById(List<String> ids) {
        return teamDao.findAllById(ids)
                      .flatMap(team -> Mono.just(team.toTeam()));
    }

    @Override
    public Mono<Team> findById(String id) {
        return teamDao.findById(id)
                      .flatMap(team -> Mono.just(team.toTeam()));
    }

    @Override
    public Mono<Team> save(Team team) {
        return teamDao.save(team.toEntity())
                      .flatMap(teamEntity -> Mono.just(teamEntity.toTeam()));
    }

    @Override
    public Mono<Team> delete(String id) {

        return teamDao.findById(id)
                      .flatMap(teamEntity -> teamDao.delete(teamEntity)
                                                    .then(Mono.just(teamEntity.toTeam())))
                      .switchIfEmpty(Mono.error(new PersistenceException("cannot find team to delete")));
    }

    public Mono<Team> findByName(String name) {
        return teamDao.findByName(name)
                      .flatMap(teamEntity -> Mono.just(teamEntity.toTeam()));
    }
}