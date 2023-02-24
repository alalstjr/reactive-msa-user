package com.jjunpro.reactive.domain.user.dto;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.type.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record CreateUserDto(

    @NotNull(message = "아이디는 필수로 입력해야합니다.")
    @Size(min = 3, max = 10, message = "최소 3글자에서 최대 10글자까지만 입력 가능합니다.")
    String username,

    @NotNull(message = "비밀번호는 필수로 입력해야합니다.")
    @Size(min = 3, message = "비밀번호가 너무 짧습니다.")
    String password,

    @NotNull(message = "확인 비밀번호는 필수로 입력해야합니다.")
    @Size(min = 3, message = "확인 비밀번호가 너무 짧습니다.")
    String passwordConfirmation,

    @NotNull(message = "닉네임은 필수로 입력해야합니다.")
    @Size(min = 3, max = 10, message = "최소 3글자에서 최대 10글자까지만 입력 가능합니다.")
    String nickname,

    String email,
    String phone,
    Role role,
    LocalDateTime createdDate,
    LocalDateTime modifiedDate,
    String teamName
) {

    public User toUser() {
        return User.builder()
                   .username(username)
                   .nickname(nickname)
                   .password(password)
                   .role(role)
                   .build();
    }
}