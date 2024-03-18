package com.europehang.europe.auth.service;

import com.europehang.europe.common.enums.ErrorCode;
import com.europehang.europe.exception.CustomException;
import com.europehang.europe.jwt.JwtTokenProvider;
import com.europehang.europe.jwt.dto.TokenDto;
import com.europehang.europe.redis.RedisUtils;
import com.europehang.europe.role.domain.Role;
import com.europehang.europe.user.domain.User;
import com.europehang.europe.user.dto.UserJoinRequestDto;
import com.europehang.europe.user.repository.UserRepository;
import com.europehang.europe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtils redisUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    // 유저 회원가입
    @Transactional
    public User userSignup(UserJoinRequestDto joinRequestDto) {
        userService.checkEmailExists(joinRequestDto.getEmail());

        Role role = Role.builder()
                .roleName("ROLE_USER")
                .build();

        User user = joinRequestDto.toEntity(passwordEncoder.encode(joinRequestDto.getPassword()), role);

        return userRepository.save(user);
    }

    public TokenDto signin(UsernamePasswordAuthenticationToken authenticationToken){

        //authenticationToken을 이용해서 Authentication 객체를 생성하려고 authenticate 메소드가 실행 될때 loadUserByUsername 메소드 실행
        // -> authentication 객체 생성
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String accessToken = jwtTokenProvider.createAccessToken(authentication);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

        redisUtils.setRefreshToken(authentication.getName(), refreshToken);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDto reissueAccessToken(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
        String storedRefreshToken = redisUtils.getRefreshTokenByKey(authentication.getName());

        if(!storedRefreshToken.equals(refreshToken)) {
            throw new CustomException(ErrorCode.MISMATCH_TOKEN);
        }

        // 새로운 Access Token 재발급
        String newAccessToken = jwtTokenProvider.createAccessToken(authentication);

        // Redis에 업데이트
        redisUtils.setRefreshToken(authentication.getName(), refreshToken);

        return TokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(storedRefreshToken)
                .build();
    }

    public void logout(String accessToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String email = authentication.getName();

        String refreshToken = redisUtils.getRefreshTokenByKey(email);
        if(refreshToken == null) {
            throw new CustomException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        redisUtils.deleteRefreshTokenByKey(email);
    }

}
