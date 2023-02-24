package com.jjunpro.reactive.application.service;

import com.jjunpro.reactive.application.exception.UserServiceException;
import com.jjunpro.reactive.domain.team.TeamUtils;
import com.jjunpro.reactive.domain.team.repository.TeamRepository;
import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.UserUtils;
import com.jjunpro.reactive.domain.user.dto.CreateUserDto;
import com.jjunpro.reactive.domain.user.dto.GetUserDto;
import com.jjunpro.reactive.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Flux<GetUserDto> findAllUsers() {
        return userRepository.findAll()
                             .flatMap(user -> Flux.just(user.toGetUserDto()));
    }

    public Mono<GetUserDto> findById(String userId) {
        return userRepository.findById(userId)
                             .map(User::toGetUserDto)
                             .switchIfEmpty(Mono.error(new UserServiceException(HttpStatus.UNPROCESSABLE_ENTITY, "id doesn't exist")));
    }

    public Mono<GetUserDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                             .map(User::toGetUserDto)
                             .switchIfEmpty(Mono.error(new UserServiceException(HttpStatus.UNPROCESSABLE_ENTITY, "username doesn't exist")));
    }

    public Mono<GetUserDto> addUser(Mono<CreateUserDto> createUserDtoMono) {

        return createUserDtoMono
            .flatMap(createUserDto -> {
                Mono<Boolean> usernameExistsMono = checkIfUsernameExists(createUserDto.username());
                Mono<Boolean> nicknameExistsMono = checkIfNicknameExists(createUserDto.nickname());

                /* 유효성 검사를 실시합니다. */
                return Mono.zip(usernameExistsMono, nicknameExistsMono)
                           .doOnEach(
                               user -> log.info("회원 [" + createUserDto.username() + "] 을 추가 시도합니다.")
                           )
                           .flatMap(zip -> {
                               Boolean usernameExists = zip.getT1();
                               Boolean nicknameExists = zip.getT2();

                               if (Boolean.TRUE.equals(usernameExists)) {
                                   return Mono.error(
                                       new UserServiceException(HttpStatus.UNPROCESSABLE_ENTITY, "Username already exists")
                                   );
                               }

                               if (Boolean.TRUE.equals(nicknameExists)) {
                                   return Mono.error(
                                       new UserServiceException(HttpStatus.UNPROCESSABLE_ENTITY, "Nickname already exists")
                                   );
                               }

                               return createUser(createUserDto);
                           });
            });
    }

    private Mono<GetUserDto> createUser(CreateUserDto createUserDto) {
        var user = createUserDto.toUser();
        return userRepository
            .save(user)
            .map(User::toGetUserDto);
    }

    public Mono<GetUserDto> deleteUser(String userId) {
        return userRepository
            .findById(userId)
            .flatMap(user -> userRepository.delete(userId)
                                           .then(deleteMemberFromTeam(user)))
            .switchIfEmpty(Mono.error(new UserServiceException(HttpStatus.UNPROCESSABLE_ENTITY, "cannot find user to delete")));
    }

    private Mono<GetUserDto> deleteMemberFromTeam(User member) {
        var teamId = UserUtils.toTeamId.apply(member);

        if (teamId != null) {
            return teamRepository
                .findById(teamId)
                .flatMap(team -> {
                    TeamUtils.toMembers.apply(team).remove(member);
                    return teamRepository.save(team)
                                         .flatMap(t -> Mono.just(member.toGetUserDto()));
                })
                .switchIfEmpty(Mono.error(new UserServiceException(HttpStatus.UNPROCESSABLE_ENTITY, "cannot find user's team")));
        }
        return Mono.just(member.toGetUserDto());
    }

    public Mono<Boolean> checkIfUsernameExists(String username) {
        return userRepository
            .findByUsername(username)
            .map(user -> true) // username 이 이미 존재하는 경우
            .defaultIfEmpty(false); // username 이 존재하지 않는 경우
    }

    public Mono<Boolean> checkIfNicknameExists(String nickname) {
        return userRepository
            .findByNickname(nickname)
            .map(user -> true) // nickname 가 이미 존재하는 경우
            .defaultIfEmpty(false); // nickname 가 존재하지 않는 경우
    }
}