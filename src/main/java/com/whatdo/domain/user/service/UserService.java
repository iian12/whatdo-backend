package com.whatdo.domain.user.service;

import com.whatdo.global.security.jwt.TokenResponseDto;
import com.whatdo.domain.user.dto.GoogleUserDto;

public interface UserService {

    TokenResponseDto processingGoogleUser(GoogleUserDto userDto);

    void updateNickname(String nickname, String token);
}
