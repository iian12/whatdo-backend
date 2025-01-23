package com.whatdo.global.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenResDto {

    private String refreshToken;
    private String client;

    @Builder
    public RefreshTokenResDto(String refreshToken, String client) {
        this.refreshToken = refreshToken;
        this.client = client;
    }
}
