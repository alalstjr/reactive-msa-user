package com.jjunpro.reactive.infrastructure.persistence.entity;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.infrastructure.persistence.configs.Base;
import javax.management.relation.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "users")
public class UserEntity extends Base {

    @Id
    String id;

    String username;
    String password;
    Role role;
    String teamId;

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
