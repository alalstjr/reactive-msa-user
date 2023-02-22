package com.jjunpro.reactive.domain.user.dto;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.type.Role;

public record CreateUserDto(String username, String password, String passwordConfirmation, Role role, String teamName) {
    public User toUser() {
        return User.builder()
                   .username(username)
                   .password(password)
                   .role(role)
                   .build();
    }
}