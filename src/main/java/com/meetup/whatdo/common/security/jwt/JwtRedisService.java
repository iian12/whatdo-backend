package com.meetup.whatdo.common.security.jwt;

import com.meetup.whatdo.user.domain.UserId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JwtRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public JwtRedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addToBlackList(String token, long ttlSeconds) {
        if (ttlSeconds > 0) {
            redisTemplate.opsForValue().set("blacklist:" + token, "true", ttlSeconds, TimeUnit.SECONDS);
        }
    }

    public void storeRefreshToken(UserId userId, String refreshToken, String tokenId, long ttlSeconds) {
        if (ttlSeconds> 0) {
            redisTemplate.opsForValue().set(
                    "refresh:" + userId + ":" + tokenId,
                    refreshToken,
                    ttlSeconds,
                    TimeUnit.SECONDS);
        }
    }

    public boolean isBlacklisted(String token) {
        return redisTemplate.hasKey("blacklist:" + token);
    }

    public String getRefreshToken(UserId userId) {
        Object refreshToken = redisTemplate.opsForValue().get("refresh:" + userId);

        return refreshToken != null ? refreshToken.toString() : null;
    }

    public void removeRefreshToken(UserId userId, String tokenId) {
        redisTemplate.delete("refresh:" + userId + ":" + tokenId);
    }

    public Boolean getCachedAccessValidity(String token) {
        Object accessValidity = redisTemplate.opsForValue().get("access:" + token);
        return accessValidity != null && Boolean.parseBoolean(accessValidity.toString());
    }
}
