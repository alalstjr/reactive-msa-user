package com.jjunpro.reactive.infrastructure.persistence.config;

import com.jjunpro.reactive.web.security.manager.AuthenticationManager;
import com.jjunpro.reactive.web.security.repository.SecurityContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final AuthenticationManager     authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint((swe, e) ->
                                          Mono.fromRunnable(() -> swe.getResponse().setStatusCode(
                                              HttpStatus.UNAUTHORIZED))
            ).accessDeniedHandler((swe, e) ->
                                      Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))
            )
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            // 로그인 없이 접근 가능한 경로 설정
            .pathMatchers(HttpMethod.DELETE, "/users/**").permitAll()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .pathMatchers("/login").permitAll()
            // 로그인이 필요한 경로 설정
            .pathMatchers(HttpMethod.GET, "/users").authenticated()
            .pathMatchers(HttpMethod.POST, "/users").authenticated()
            .anyExchange().authenticated()
            .and()
            .build();
    }
}