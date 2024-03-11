package com.europehang.europe.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private final String grantType = "Beaer"; // Beaer를 사용
    private String accessToken;
    private String refreshToken;
}
