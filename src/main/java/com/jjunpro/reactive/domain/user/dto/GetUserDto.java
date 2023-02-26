package com.jjunpro.reactive.domain.user.dto;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.type.UserRole;
import java.time.LocalDateTime;
import java.util.List;

public record GetUserDto(String id, String username, String nickname, String password, List<UserRole> userRoles, String teamId, LocalDateTime createdDate) {

    public User toUser() {
        return User
            .builder()
            .id(id)
            .username(username)
            .nickname(nickname)
            .password(password)
            .userRoles(userRoles)
            .teamId(teamId)
            .createdDate(createdDate)
            .build();
    }
}