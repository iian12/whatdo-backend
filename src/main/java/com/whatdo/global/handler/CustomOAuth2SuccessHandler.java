package com.whatdo.global.handler;

import com.whatdo.global.auth.service.CustomUserDetail;
import com.whatdo.global.config.ClientConfig;
import com.whatdo.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    public CustomOAuth2SuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

        String userId = userDetail.getUserId();

        String accessToken = jwtTokenProvider.createAccessToken(userId, ClientConfig.WEB);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId, ClientConfig.WEB);

        Cookie accessTokenCookie = createCookie("access_token", accessToken);
        Cookie refreshTokenCookie = createCookie("refresh_token", refreshToken);

        response.setStatus(HttpServletResponse.SC_OK);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private Cookie createCookie(String name, String token) {
        if (name.equals("access_token")) {
            Cookie cookie = new Cookie(name, token);
            cookie.setHttpOnly(false);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 2);
            return cookie;
        } else {
            Cookie cookie = new Cookie(name, token);
            cookie.setHttpOnly(false);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 14);
            return cookie;
        }
    }
}
