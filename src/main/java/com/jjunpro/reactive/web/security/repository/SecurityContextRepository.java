package com.jjunpro.reactive.web.security.repository;

import com.jjunpro.reactive.web.security.manager.AuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 토큰을 가져오기 위한 SecurityContextRepository 구현을 만들고 ServerSecurityContextRepository 에 전달합니다.
 * @author jjunpro
 * @since 2023/02/26 PM 15:33
 */
@Component
@AllArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        return Mono
            .justOrEmpty(swe.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
            .filter(authHeader -> authHeader.startsWith("Bearer "))
            .flatMap(
               authHeader -> {
                   String         authToken = authHeader.substring(7);
                   Authentication auth      = new UsernamePasswordAuthenticationToken(authToken, authToken);
                   return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
               }
            );
    }
}