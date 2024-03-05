package com.europehang.europe.user.service;

import com.europehang.europe.common.util.SecurityUtil;
import com.europehang.europe.exception.CustomException;
import com.europehang.europe.role.domain.Role;
import com.europehang.europe.user.domain.User;
import com.europehang.europe.user.dto.UserJoinRequestDto;
import com.europehang.europe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.europehang.europe.common.enums.ErrorCode.USER_ALREADY_EXIST;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 회원가입
    @Transactional
    public User userSignup(UserJoinRequestDto joinRequestDto) {
        checkEmailExists(joinRequestDto.getEmail());

        Role role = Role.builder()
                .roleName("ROLE_USER")
                .build();

        User user = joinRequestDto.toEntity(passwordEncoder.encode(joinRequestDto.getPassword()), role);

        return userRepository.save(user);

    }

    public Optional<User> getUserWithRoles(String email) {
        return userRepository.findOneWithRolesByEmail(email);
    }

    public Optional<User> getMyUserWithRoles(){
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithRolesByEmail);
    }

    public void checkEmailExists(String email) {
        userRepository.findOneWithRolesByEmail(email)
                .ifPresent(user -> {
                    throw new CustomException(USER_ALREADY_EXIST);
                });
    }

    public boolean checkDuplicationEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkNicknameExist(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
