package com.jjunpro.reactive.domain.user.dto;

import com.jjunpro.reactive.domain.user.User;
import javax.management.relation.Role;

public record GetUserDto(String id, String username, String password, Role role, String teamId) {

    public User toUser() {
        return User.builder()
                   .id(id)
                   .username(username)
                   .password(password)
                   .role(role)
                   .teamId(teamId)
                   .build();
    }
}