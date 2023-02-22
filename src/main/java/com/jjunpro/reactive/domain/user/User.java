package com.jjunpro.reactive.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jjunpro.reactive.domain.user.dto.GetUserDto;
import com.jjunpro.reactive.domain.user.type.Role;
import com.jjunpro.reactive.infrastructure.persistence.entity.UserEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Builder
@EqualsAndHashCode
public class User {

    String id;
    String username;
    String password;
    Role   role;
    String teamId;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime modifiedDate;

    public User withTeamId(String newTeamId) {
        return User.builder()
                   .id(id)
                   .username(username)
                   .password(password)
                   .role(role)
                   .teamId(newTeamId)
                   .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                         .id(id)
                         .username(username)
                         .password(password)
                         .role(role)
                         .teamId(teamId)
                         .build();
    }

    public GetUserDto toGetUserDto() {
        return new GetUserDto(id, username, password, role, teamId);
    }
}
