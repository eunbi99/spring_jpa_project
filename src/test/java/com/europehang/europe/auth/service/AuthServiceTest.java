package com.europehang.europe.auth.service;

import com.europehang.europe.common.enums.Gender;
import com.europehang.europe.user.dto.UserJoinRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@SpringBootTest
@Transactional
@Rollback(value = false)
class AuthServiceTest {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    private static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Autowired
    private AuthService authService;

    @Test
    public void 회원가입_실패() {
        UserJoinRequestDto userDto = UserJoinRequestDto.builder()
                .email("tesst")
                .username("")
                .password("akstp123!")
                .gender(Gender.valueOf("FEMALE"))
                .nickname("테스트1")
                .build();

        authService.userSignup(userDto);

        Set<ConstraintViolation<UserJoinRequestDto>> violations = validator.validate(userDto);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertEquals(violations.size(),1);

    }
}