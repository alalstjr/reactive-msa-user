package com.jjunpro.reactive.infrastructure.persistence.entity;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.type.Role;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    String id;
    String        username;
    String        password;
    String        nickname;
    String        email;
    String        phone;
    Role          role;
    String        teamId;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    public User toUser() {
        return User.builder()
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
}
