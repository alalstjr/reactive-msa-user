package com.jjunpro.reactive.domain.user;

import com.jjunpro.reactive.domain.user.dto.GetUserDto;
import com.jjunpro.reactive.domain.user.type.Role;
import com.jjunpro.reactive.infrastructure.persistence.entity.UserEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class User {

    String        id;
    String        username;
    String        password;
    String        nickname;
    String        email;
    String        phone;
    Role          role;
    String        teamId;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    public User withTeamId(String newTeamId) {
        return User
            .builder()
            .id(id)
            .email(email)
            .nickname(nickname)
            .password(password)
            .role(role)
            .teamId(newTeamId)
            .build();
    }

    public UserEntity toEntity() {
        return UserEntity
            .builder()
            .id(id)
            .username(username)
            .password(password)
            .nickname(nickname)
            .email(email)
            .phone(phone)
            .role(role)
            .teamId(teamId)
            .createdDate(createdDate)
            .modifiedDate(modifiedDate)
            .build();
    }

    public GetUserDto toGetUserDto() {
        return new GetUserDto(id, username, nickname, password, role, teamId, createdDate);
    }
}
