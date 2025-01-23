package com.whatdo.domain.user.service;

import com.whatdo.global.security.jwt.TokenResponseDto;
import com.whatdo.domain.user.model.GoogleUserDto;

public interface UserService {

    TokenResponseDto processingGoogleUser(GoogleUserDto userDto);
}
