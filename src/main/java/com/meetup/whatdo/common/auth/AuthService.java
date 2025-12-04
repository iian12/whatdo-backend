package com.meetup.whatdo.common.auth;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.meetup.whatdo.user.domain.repository.UserRepository;
import com.meetup.whatdo.common.security.jwt.JwtRedisService;
import com.meetup.whatdo.common.security.jwt.JwtService;
import com.meetup.whatdo.common.security.jwt.JwtTokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtService jwtService;
    private final JwtRedisService jwtRedisService;

    public AuthService(JwtTokenProvider jwtTokenProvider,
                       UserRepository userRepository,
                       RedisTemplate<String, Object> redisTemplate,
                       JwtService jwtService,
                       JwtRedisService jwtRedisService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.jwtService = jwtService;
        this.jwtRedisService = jwtRedisService;
    }


}
