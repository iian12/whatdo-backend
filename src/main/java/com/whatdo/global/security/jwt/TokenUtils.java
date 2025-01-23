package com.whatdo.global.security.jwt;

import com.whatdo.global.utils.EncryptionUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

public class TokenUtils {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private static Key key;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getUrlDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    private TokenUtils() {
    }

    public static String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue(); // Return cookie value
                }
            }
        }

        return null;
    }

    public static String getUserIdFromToken(String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

            String encryptedUserId = claims.getSubject();
            if (encryptedUserId == null) {
                throw new IllegalArgumentException("User ID in token cannot be null or empty");
            }
            return EncryptionUtils.decrypt(encryptedUserId);

        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred", e);
        }
    }

    public static String getClientTypeFromToken(String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            if (token == null || token.isEmpty()) {
                throw new IllegalArgumentException("Token cannot be null or empty");
            }

            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

            return claims.get("clientType", String.class);
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred", e);
        }
    }
}
