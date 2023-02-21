package com.jjunpro.reactive.application.service;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.dto.UserDto;
import com.jjunpro.reactive.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<UserDto> getUser(String userId) {
        return userRepository
            .findById(userId)
            .map(UserDto::entityToDto)
            .switchIfEmpty(
                Mono.error(new UserServiceException("id doesn't exist"))
            );
    }
}