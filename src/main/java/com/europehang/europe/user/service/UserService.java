package com.europehang.europe.user.service;

import com.europehang.europe.user.domain.User;
import com.europehang.europe.user.dto.UserJoinRequestDto;
import com.europehang.europe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    //private BCryptPasswordEncoder passwordEncoder;

    // 유저 회원가입
    public Long userJoin(UserJoinRequestDto joinRequestDto) {
        User user = joinRequestDto.toEntity();
        userRepository.save(user);

        return user.getId();
    }

    // 유저 로그인

    // 이메일이 존재하는지 체크
    public boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    //닉네임 체
    public boolean checkExistNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
    //
}
