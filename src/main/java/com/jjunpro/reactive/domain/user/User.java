package com.jjunpro.reactive.domain.user;

import javax.management.relation.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    String id;
    String username;
    String password;
    Role   role;
    String teamId;

    public User withTeamId(String newTeamId) {
        return User.builder()
                   .id(id)
                   .username(username)
                   .password(password)
                   .role(role)
                   .teamId(newTeamId)
                   .build();
    }
}
