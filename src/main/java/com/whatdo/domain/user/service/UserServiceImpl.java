package com.whatdo.domain.user.service;

import com.whatdo.domain.user.model.GoogleUserDto;
import com.whatdo.domain.user.model.Provider;
import com.whatdo.domain.user.model.Role;
import com.whatdo.domain.user.model.Users;
import com.whatdo.domain.user.repository.UserRepository;
import com.whatdo.global.config.ClientConfig;
import com.whatdo.global.security.jwt.JwtTokenProvider;
import com.whatdo.global.security.jwt.TokenResponseDto;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public TokenResponseDto processingGoogleUser(GoogleUserDto userDto) {
        Optional<Users> optionalUser = userRepository.findByEmail(userDto.getEmail());

        Users user = optionalUser.orElseGet(() -> createNewGoogleUser(userDto));
        return generateTokenResponse(user.getId());
    }

    private Users createNewGoogleUser(GoogleUserDto userDto) {
        Users newUser = Users.builder()
            .email(userDto.getEmail())
            .profileImgUrl(userDto.getProfileImgUrl())
            .provider(Provider.GOOGLE)
            .subjectId(userDto.getSubjectId())
            .role(Role.USER)
            .build();

        return userRepository.save(newUser);
    }

    private TokenResponseDto generateTokenResponse(String userId) {
        String accessToken = jwtTokenProvider.createAccessToken(userId, ClientConfig.APP);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId, ClientConfig.APP);

        return TokenResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
