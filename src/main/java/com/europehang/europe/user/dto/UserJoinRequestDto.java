package com.europehang.europe.user.dto;

import com.europehang.europe.common.enums.Gender;
import com.europehang.europe.role.domain.Role;
import com.europehang.europe.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Collections;
import java.util.Set;

@Getter
@NoArgsConstructor
@Builder
public class UserJoinRequestDto {
    @NotBlank(message = "이메일은 필수 입력값 입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력값 입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,15}$", message = "비밀번호는 6~15자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    private Gender gender;

    @NotBlank(message = "닉네임은 필수 입력값 입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$" , message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
    private String nickname;

    public UserJoinRequestDto(String email, String username, String password,Gender gender, String nickname) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.nickname = nickname;
    }

    // DTO -> entity 변경
    public User toEntity(String encryptPassword, Role role) {
        return User.builder()
                .email(email)
                .username(username)
                .password(encryptPassword)
                .gender(gender)
                .nickname(nickname)
                .roles(Collections.singleton(role))
                .build();
    }
}
