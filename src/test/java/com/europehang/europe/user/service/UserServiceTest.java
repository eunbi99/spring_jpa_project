package com.europehang.europe.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@Rollback(value = false)
class UserServiceTest {

//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

//    @Test
//    public void 회원가입() {
//        UserJoinRequestDto userDto = UserJoinRequestDto.builder()
//                .email("test111@gmail.com")
//                .username("테스트")
//                .password("!Test1234")
//                .gender(Gender.FEMALE)
//                .nickname("테스트1")
//                .build();
//
//        Long userId = userService.userJoin(userDto);
//        Assertions.assertEquals(userId, 1);

//    }

}