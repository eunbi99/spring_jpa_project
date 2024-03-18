package com.europehang.europe.common.interceptor;

import com.europehang.europe.common.enums.ErrorCode;
import com.europehang.europe.exception.CustomException;
import com.europehang.europe.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 토큰 유무 확인
        if(!StringUtils.hasText(authorizationHeader)) {
            throw new CustomException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        String[] authorizations = authorizationHeader.split(" ");
        String token = authorizations[1];

        // 토큰 타입 확인
        if(authorizations.length < 2 || !authorizations[0].equals("Bearer")) {
            throw new CustomException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }

        // 토큰 유효성 검증
        jwtTokenProvider.validateToken(token);


        return true;
    }
}
