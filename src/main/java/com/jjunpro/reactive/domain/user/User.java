package com.jjunpro.reactive.domain.user;

import com.jjunpro.reactive.domain.user.dto.GetUserDto;
import com.jjunpro.reactive.domain.user.type.UserRole;
import com.jjunpro.reactive.infrastructure.persistence.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import reactor.core.publisher.Mono;

@Builder
@EqualsAndHashCode
public class User {

    String         id;
    String         username;
    String         password;
    String         nickname;
    String         email;
    String         phone;
    List<UserRole> userRoles;
    String         teamId;
    Boolean        enabled;
    LocalDateTime  createdDate;
    LocalDateTime  modifiedDate;

    public User withTeamId(String newTeamId) {
        return User
            .builder()
            .id(id)
            .email(email)
            .nickname(nickname)
            .password(password)
            .userRoles(userRoles)
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
            .userRoles(userRoles)
            .teamId(teamId)
            .createdDate(createdDate)
            .modifiedDate(modifiedDate)
            .build();
    }

    public GetUserDto toGetUserDto() {
        return new GetUserDto(id, username, nickname, password, userRoles, teamId, createdDate);
    }

    public Mono<GetUserDto> toGetUserDtoMono() {
        return Mono.fromCallable(() -> new GetUserDto(id, username, nickname, password, userRoles, teamId, createdDate));
    }
}
