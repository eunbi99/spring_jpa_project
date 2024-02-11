package com.europehang.europe.user.controller;

import com.europehang.europe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//    @PostMapping("/join")
//    public ApiResponse Join(UserJoinRequestDto userJoinRequestDto) {
//        userService.userJoin(userJoinRequestDto);
//        return ApiResponse.success(200,"ok");
//    }
}
