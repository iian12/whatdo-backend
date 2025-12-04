package com.meetup.whatdo.common.security.jwt;

import com.meetup.whatdo.user.domain.UserId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtRedisService jwtRedisService;
    private final JwtKeyProvider jwtKeyProvider;
    private Key key;

    public JwtService(JwtTokenProvider jwtTokenProvider,
                      JwtRedisService jwtRedisService,
                      JwtKeyProvider jwtKeyProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtRedisService = jwtRedisService;
        this.jwtKeyProvider = jwtKeyProvider;
    }

    @PostConstruct
    public void init() {
        this.key = jwtKeyProvider.getKey();
    }

    private boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (MalformedJwtException | IllegalArgumentException | ExpiredJwtException e) {
            return false;
        }
    }

    public boolean validate(String token) {
        if (jwtRedisService.isBlacklisted(token)) return false;
        return validateToken(token);
    }

    public String reissueAccessToken(String refreshToken) {
        String userId = getClaims(refreshToken).getSubject();
        return jwtTokenProvider.createAccessToken(UserId.of(Long.parseLong(userId)));
    }

    public Long getUserId(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

    public String getTokenId(String token) {
        return getClaims(token).getId();
    }

    private Claims getClaims(String token) {
        if (token.startsWith("Bearer ")) token = token.substring(7);
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getRemainingTTL(String token) {
        long now = System.currentTimeMillis();

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return Math.max(0, (claims.getExpiration().getTime() - now) / 1000);

    }
}
