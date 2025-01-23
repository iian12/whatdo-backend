package com.whatdo.global.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IdTokenDto {

    private String idToken;

    @Builder
    public IdTokenDto(String idToken) {
        this.idToken = idToken;
    }
}
