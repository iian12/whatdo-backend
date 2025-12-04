package com.meetup.whatdo.common.security.jwt;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtKeyProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Getter
    private Key key;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = secretKey.getBytes();
        key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }
}
