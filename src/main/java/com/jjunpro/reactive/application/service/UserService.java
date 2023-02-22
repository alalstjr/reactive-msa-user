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
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Mono<GetUserDto> findById(String userId) {
        return userRepository.findById(userId)
                             .map(User::toGetUserDto)
                             .switchIfEmpty(Mono.error(new UserServiceException("id doesn't exist")));
    }

    public Mono<GetUserDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                             .map(User::toGetUserDto)
                             .switchIfEmpty(Mono.error(new UserServiceException("username doesn't exist")));
    }

    public Mono<GetUserDto> addUser(Mono<CreateUserDto> createUserDtoMono) {
        return createUserDtoMono
            .flatMap(createUserDto -> userRepository
                .findByUsername(createUserDto.username())
                .hasElement()
                .flatMap(isUserPresent -> isUserPresent
                    ?
                    Mono.error(new UserServiceException("user with username " + createUserDto.username() + " already exists"))
                    :
                        createUser(createUserDto)));
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
            .switchIfEmpty(Mono.error(new UserServiceException("cannot find user to delete")));
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
                .switchIfEmpty(Mono.error(new UserServiceException("cannot find user's team")));
        }
        return Mono.just(member.toGetUserDto());
    }
}