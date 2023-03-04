package com.jjunpro.reactive.domain.user.dto;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.type.UserRole;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public record CreateUserDto(
    String username,
    String password,
    String passwordConfirmation,
    String nickname,
    String email,
    String phone,
    LocalDateTime createdDate,
    LocalDateTime modifiedDate,
    String teamName
) {
    public Mono<User> toUser(PasswordEncoder passwordEncoder) {
        Mono<String> encodedPasswordMono = Mono.fromCallable(() -> passwordEncoder.encode(password)).subscribeOn(Schedulers.boundedElastic());
        return encodedPasswordMono.flatMap(encodedPassword -> Mono.fromCallable(() -> User
            .builder()
            .username(username)
            .nickname(nickname)
            .password(encodedPassword)
            .userRoles(List.of(UserRole.ROLE_USER))
            .createdDate(LocalDateTime.now())
            .build()));
    }
}