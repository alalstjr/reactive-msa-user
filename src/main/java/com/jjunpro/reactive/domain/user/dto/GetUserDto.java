package com.jjunpro.reactive.domain.user.dto;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.type.Role;
import java.time.LocalDateTime;

public record GetUserDto(String id, String username, String nickname, String password, Role role, String teamId, LocalDateTime createdDate) {

    public User toUser() {
        return User
            .builder()
            .id(id)
            .username(username)
            .nickname(nickname)
            .password(password)
            .role(role)
            .teamId(teamId)
            .createdDate(createdDate)
            .build();
    }
}