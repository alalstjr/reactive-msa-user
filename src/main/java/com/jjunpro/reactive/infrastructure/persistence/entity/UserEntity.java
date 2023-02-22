package com.jjunpro.reactive.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.type.Role;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    String id;

    String username;
    String password;
    Role   role;
    String teamId;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDate;

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
