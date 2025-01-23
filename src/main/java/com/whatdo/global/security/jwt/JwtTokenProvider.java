package com.whatdo.global.security.jwt;

import com.whatdo.domain.user.model.Users;
import com.whatdo.domain.user.repository.UserRepository;
import com.whatdo.global.auth.RefreshTokenResDto;
import com.whatdo.global.config.ClientConfig;
import com.whatdo.global.utils.EncryptionUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final UserRepository userRepository;
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-validity-in-ms}")
    private long accessTokenValidityInMs;

    @Value("${jwt.refresh-token-validity-in-ms}")
    private long refreshTokenValidityInMs;

    private Key key;

    public JwtTokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getUrlDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String userId, ClientConfig config) {
        try {
            if (userId == null) {
                throw new IllegalArgumentException("userId is null");
            }

            Map<String, Object> claims = new HashMap<>();

            String encryptedUserId = EncryptionUtils.encrypt(userId);

            Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid UserID"));

            claims.put("role", user.getRole());
            claims.put("clientType", config.toString());
            Date now = new Date();
            Date validity = new Date(now.getTime() + accessTokenValidityInMs);

            return Jwts.builder()
                .setClaims(claims)
                .setSubject(encryptedUserId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create access token", e);
        }
    }

    public String createRefreshToken(String userId, ClientConfig config) {
        try {
            String encryptedMemberId = EncryptionUtils.encrypt(userId);

            Date now = new Date();
            Date validity = new Date(now.getTime() + refreshTokenValidityInMs);

            Map<String, Object> claims = new HashMap<>();
            claims.put("clientType", config.toString());

            return Jwts.builder()
                .setClaims(claims)
                .setSubject(encryptedMemberId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create refresh token", e);
        }
    }

    public boolean validateToken(String token) {
        String clearedToken = token.substring(7);

        try {
            Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(clearedToken);

            return true;
        } catch (SignatureException | MalformedJwtException | IllegalArgumentException |
                 ExpiredJwtException e) {
            return false;
        }
    }

    public RefreshTokenResDto refreshAccessToken(String refreshToken) {

        RefreshTokenResDto resDto;

        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException("Refresh token expired");
            }

            String encryptedUserId = claims.getSubject();
            String userId = EncryptionUtils.decrypt(encryptedUserId);

            String clientType = TokenUtils.getClientTypeFromToken(refreshToken);

            if (Objects.equals(clientType, "APP")) {
                String token = createAccessToken(userId, ClientConfig.APP);
                resDto = RefreshTokenResDto.builder()
                    .refreshToken(token)
                    .client("APP")
                    .build();
            } else {
                String token = createAccessToken(userId, ClientConfig.WEB);
                resDto = RefreshTokenResDto.builder()
                    .refreshToken(token)
                    .client("WEB")
                    .build();
            }

            return resDto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to refresh access token", e);
        }
    }
}
