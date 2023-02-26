package com.jjunpro.reactive.web.security.manager;

import com.jjunpro.reactive.web.security.util.JWTUtil;
import io.jsonwebtoken.Claims;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 토큰 및 역할 유효성 검사를 위한 구현체
 * @author jjunpro
 * @since 2023/02/26 PM 15:33
 */
@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private JWTUtil jwtUtil;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwtUtil.getUsernameFromToken(authToken);

        return Mono
            .just(jwtUtil.validateToken(authToken))
            .filter(valid -> valid)
            .switchIfEmpty(Mono.empty())
            .map(
                valid -> {
                    Claims       claims   = jwtUtil.getAllClaimsFromToken(authToken);
                    List<String> rolesMap = claims.get("role", List.class);
                    return new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        rolesMap.stream().map(SimpleGrantedAuthority::new).toList()
                    );
                }
            );
    }
}