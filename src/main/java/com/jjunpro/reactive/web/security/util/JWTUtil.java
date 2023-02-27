package com.jjunpro.reactive.web.security.util;

import com.jjunpro.reactive.domain.user.dto.GetUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JWToken 전체적인 설정 정보
 * @author jjunpro
 * @since 2023/02/26 PM 15:33
 */
@Component
public class JWTUtil {

    @Value("${springbootjjwt.jjwt.secret}")
    private String secret;

    @Value("${springbootjjwt.jjwt.expiration}")
    private String expirationTime;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(GetUserDto user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.userRoles());
        claims.put("_id", user.id());
        return doGenerateToken(claims, user.username());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        long       expirationTimeLong = Long.parseLong(expirationTime); //in second
        final Date createdDate        = new Date();
        final Date expirationDate     = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(key)
            .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}