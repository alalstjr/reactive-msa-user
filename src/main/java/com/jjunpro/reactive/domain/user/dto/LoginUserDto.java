package com.jjunpro.reactive.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginUserDto(
    @NotNull(message = "아이디는 필수로 입력해야합니다.")
    @Size(min = 3, max = 20, message = "최소 3글자에서 최대 10글자까지만 입력 가능합니다.")
    String username,

    @NotNull(message = "비밀번호는 필수로 입력해야합니다.")
    @Size(min = 3, message = "비밀번호가 너무 짧습니다.")
    String password
) {

}
