package com.jjunpro.reactive.infrastructure.persistence.entity;

import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.domain.user.type.UserRole;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity implements UserDetails {

    @Id
    private String         id;
    private String         username;
    private String         password;
    private String         nickname;
    private String         email;
    private String         phone;
    private List<UserRole> userRoles;
    private String         teamId;
    private LocalDateTime  createdDate;
    private LocalDateTime  modifiedDate;
    private Boolean        enabled;

    public Mono<User> toUser() {
        return Mono.fromCallable(() -> User
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
            .build());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userRoles.stream().map(
            authority ->
                new SimpleGrantedAuthority(
                    authority.name()
                )
        )
        .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
