package com.europehang.europe.redis;

import com.europehang.europe.common.enums.ErrorCode;
import com.europehang.europe.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisUtils {
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refresh_token_validity_in_seconds;

    public void setRefreshToken(String email, String refreshToken) {
        redisTemplate.opsForValue().set(
                email,
                refreshToken,
                refresh_token_validity_in_seconds,
                TimeUnit.SECONDS
        );
    }

    public String getRefreshTokenByKey(String email) {
       return redisTemplate.opsForValue().get(email);
    }

    public void deleteRefreshTokenByKey(String email) {
        redisTemplate.delete(email);
    }

}
