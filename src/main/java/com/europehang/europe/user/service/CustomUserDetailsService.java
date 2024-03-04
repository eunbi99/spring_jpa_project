package com.europehang.europe.user.service;

import com.europehang.europe.exception.CustomException;
import com.europehang.europe.user.domain.User;
import com.europehang.europe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.europehang.europe.common.enums.ErrorCode.USER_NOT_FOUND;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * email을 통해 DB에서 유저정보와 권한정보를 가져온다.
     * 해당 정보를 가지고 createUser 메서드를 실행해 userDetails.User 객체를 생성해서 리턴한다.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return userRepository.findOneWithRolesByEmail(email)
                .map(user -> createUser(email, user))
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private org.springframework.security.core.userdetails.User createUser(String email, User user) {
        if(user.getIsActive().equals('N')){
            throw new RuntimeException(email + "활성화 되어있지 않은 회원입니다.");
        }
        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                grantedAuthorities);

    }
}
