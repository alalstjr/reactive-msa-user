package com.jjunpro.reactive.infrastructure.persistence.repository;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.repository.UserRepository;
import com.jjunpro.reactive.infrastructure.persistence.dao.UserDao;
import com.jjunpro.reactive.infrastructure.persistence.exception.PersistenceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    @Override
    public Flux<User> findAll() {
        return userDao.findAll()
                      .flatMap(user -> Mono.just(user.toUser()));
    }

    @Override
    public Flux<User> findAllById(List<String> ids) {
        return userDao.findAllById(ids)
                      .flatMap(user -> Mono.just(user.toUser()));
    }

    @Override
    public Mono<User> findById(String id) {
        return userDao.findById(id)
                      .flatMap(user -> Mono.just(user.toUser()));
    }

    @Override
    public Mono<User> save(User user) {
        return userDao.save(user.toEntity())
                      .flatMap(userEntity -> Mono.just(userEntity.toUser()));
    }


    public Flux<User> findByTeamId(String teamId) {
        return userDao.findByTeamId(teamId)
                      .flatMap(user -> Mono.just(user.toUser()));
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userDao.findByUsername(username)
                      .flatMap(user -> Mono.just(user.toUser()));
    }

    @Override
    public Mono<User> findByNickname(String nickname) {
        return userDao.findByNickname(nickname)
            .flatMap(user -> Mono.just(user.toUser()));
    }

    @Override
    public Flux<User> saveAll(List<User> users) {
        return userDao
            .saveAll(users.stream().map(User::toEntity).toList())
            .flatMap(userEntity -> Mono.just(userEntity.toUser()));
    }

    @Override
    public Mono<User> delete(String id) {

        return userDao.findById(id)
                      .flatMap(userEntity -> userDao.delete(userEntity)
                                                    .then(Mono.just(userEntity.toUser())))
                      .switchIfEmpty(Mono.error(new PersistenceException("cannot find user to delete")));
    }

    @Override
    public Mono<Void> deleteAll(List<User> users) {
        return userDao
            .deleteAll(users.stream().map(User::toEntity).toList());
    }
}