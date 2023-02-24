package com.jjunpro.reactive.domain.user.dto;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.type.Role;

public record GetUserDto(String id, String username, String nickname, String password, Role role, String teamId) {

    public User toUser() {
        return User.builder()
                   .id(id)
                   .username(username)
                   .nickname(nickname)
                   .password(password)
                   .role(role)
                   .teamId(teamId)
                   .build();
    }
}