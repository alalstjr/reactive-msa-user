package com.jjunpro.reactive.domain.user.dto;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.type.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record CreateUserDto(
    @NotNull(message = "아이디는 필수로 입력해야합니다.") String username,
    String password,
    String passwordConfirmation,
    String nickname,
    String email,
    @NotNull(message = "핸드폰번호는 필수로 입력해야합니다.")
    @Size(min = 2, max = 14)
    String phone,
    Role role,
    LocalDateTime createdDate,
    LocalDateTime modifiedDate,
    String teamName
) {

    public User toUser() {
        return User.builder()
                   .username(username)
                   .password(password)
                   .role(role)
                   .build();
    }
}