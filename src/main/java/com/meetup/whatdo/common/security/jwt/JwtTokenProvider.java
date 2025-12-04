package com.meetup.whatdo.common.security.jwt;

import com.meetup.whatdo.user.domain.UserId;
import com.meetup.whatdo.user.domain.Users;
import com.meetup.whatdo.user.domain.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    private final UserRepository userRepository;
    private final JwtKeyProvider jwtKeyProvider;
    private Key key;

    public JwtTokenProvider(UserRepository userRepository, JwtKeyProvider jwtKeyProvider) {
        this.userRepository = userRepository;
        this.jwtKeyProvider = jwtKeyProvider;
    }

    @PostConstruct
    public void init() {
        this.key = jwtKeyProvider.getKey();
    }

    @Value("${jwt.access-token-validity-in-ms}")
    private int accessTokenValidityInMs;

    @Value("${jwt.refresh-token-validity-in-ms}")
    private int refreshTokenValidityInMs;

    public String createAccessToken(UserId userId) {
        try {
            if (userId == null) {
                throw new IllegalArgumentException("userId is null");
            }

            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("user not found"));

            Date now = new Date();
            Date validity = new Date(now.getTime() + accessTokenValidityInMs);

            return Jwts.builder()
                    .setSubject(userId.toString())
                    .claim("role", user.getRole().toString())
                    .setIssuedAt(now)
                    .setExpiration(validity)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public String createRefreshToken(UserId userId) {
        try {
            Date now = new Date();
            Date validity = new Date(now.getTime() + refreshTokenValidityInMs);

            String tokenId = UUID.randomUUID().toString();

            return Jwts.builder()
                    .setSubject(userId.toString())
                    .setId(tokenId)
                    .setIssuedAt(now)
                    .setExpiration(validity)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
