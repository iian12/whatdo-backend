package com.whatdo.global.auth.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.io.BaseEncoding;
import com.whatdo.domain.user.model.GoogleUserDto;
import com.whatdo.domain.user.service.UserService;
import com.whatdo.global.auth.IdTokenDto;
import com.whatdo.global.auth.RefreshTokenResDto;
import com.whatdo.global.security.jwt.JwtTokenProvider;
import com.whatdo.global.security.jwt.TokenResponseDto;
import com.whatdo.global.security.jwt.TokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${client-id}")
    private String CLIENT_ID;

    @PostMapping("/google/login")
    public ResponseEntity<String> googleLogin(@RequestBody IdTokenDto idTokenDto,
        HttpServletResponse response) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY)
                .setAudience(Collections.singletonList(CLIENT_ID)).build();

            String cleanedIdToken = idTokenDto.getIdToken();
            GoogleIdToken token = verifier.verify(cleanedIdToken);

            if (token != null) {
                GoogleIdToken.Payload payload = token.getPayload();
                String email = payload.getEmail();
                String profileImagUrl = (String) payload.get("picture");
                String nickname = (String) payload.get("name");
                String subjectId = payload.getSubject();

                GoogleUserDto userDto = GoogleUserDto.builder()
                    .email(email)
                    .profileImgUrl(profileImagUrl)
                    .nickname(nickname)
                    .subjectId(subjectId)
                    .build();

                TokenResponseDto tokenResponseDto = userService.processingGoogleUser(userDto);

                response.setHeader(
                    "Authorization", "Bearer " + tokenResponseDto.getAccessToken());
                response.setHeader(
                    "Refresh-Token", "Bearer " + tokenResponseDto.getRefreshToken());
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (BaseEncoding.DecodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateAccessToken(HttpServletRequest request) {
        try {
            boolean isValid = jwtTokenProvider.validateToken(
                Objects.requireNonNull(TokenUtils.extractTokenFromRequest(request)));

            if (isValid) {
                return ResponseEntity.ok("Access token is valid");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Access token is not valid");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest request,
        HttpServletResponse response) {
        String token = TokenUtils.extractTokenFromRequest(request);

        try {
            RefreshTokenResDto refreshAccessToken = jwtTokenProvider.refreshAccessToken(token);

            if (refreshAccessToken.getClient().equals("APP")) {
                response.setHeader(
                    "Authorization", "Bearer " + refreshAccessToken.getRefreshToken());
            } else {
                response.addCookie(createAccessTokenCookie(refreshAccessToken.getRefreshToken()));
            }

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    private Cookie createAccessTokenCookie(String token) {
        Cookie cookie = new Cookie("access_token", token);

        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 2);

        return cookie;
    }
}
