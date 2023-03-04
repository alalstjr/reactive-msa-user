package com.jjunpro.reactive.infrastructure.persistence.repository;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.repository.UserRepository;
import com.jjunpro.reactive.infrastructure.persistence.dao.UserDao;
import com.jjunpro.reactive.infrastructure.persistence.entity.UserEntity;
import com.jjunpro.reactive.infrastructure.persistence.exception.PersistenceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 회원 정보를 DB 에 직접 연결해서 가져오는 저장소
 * @author jjunpro
 * @since 2023/02/26 PM 15:33
 */
@Log4j2
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    @Override
    public Flux<User> findAll() {
        return userDao
            .findAll()
            .flatMap(UserEntity::toUser);
    }

    @Override
    public Flux<User> findAllById(List<String> ids) {
        return userDao
            .findAllById(ids)
            .flatMap(UserEntity::toUser);
    }

    @Override
    public Mono<User> findById(String id) {
        return userDao
            .findById(id)
            .flatMap(UserEntity::toUser)
            ;
    }

    @Override
    public Mono<User> save(User user) {
        return userDao
            .save(user.toEntity())
            .flatMap(UserEntity::toUser);
    }


    public Flux<User> findByTeamId(String teamId) {
        return userDao
            .findByTeamId(teamId)
            .flatMap(UserEntity::toUser);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userDao
            .findByUsername(username)
            .flatMap(UserEntity::toUser);
    }

    @Override
    public Mono<User> findByNickname(String nickname) {
        return userDao
            .findByNickname(nickname)
            .flatMap(UserEntity::toUser);
    }

    @Override
    public Flux<User> saveAll(List<User> users) {
        return userDao
            .saveAll(users.stream().map(User::toEntity).toList())
            .flatMap(UserEntity::toUser);
    }

    @Override
    public Mono<User> delete(String id) {
        return userDao
            .findById(id)
            .flatMap(
                userEntity -> userDao.delete(userEntity).then(userEntity.toUser())
            )
            .switchIfEmpty(Mono.error(new PersistenceException("cannot find user to delete")));
    }

    @Override
    public Mono<Void> deleteAll(List<User> users) {
        return userDao
            .deleteAll(users.stream().map(User::toEntity).toList());
    }
}