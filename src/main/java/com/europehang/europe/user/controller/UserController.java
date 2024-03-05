package com.europehang.europe.user.controller;

import com.europehang.europe.common.dto.ApiResponseDto;
import com.europehang.europe.common.enums.ResponseStatus;
import com.europehang.europe.user.dto.UserJoinRequestDto;
import com.europehang.europe.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/signUp")
    public ResponseEntity<ApiResponseDto> signup(@RequestBody @Valid UserJoinRequestDto userJoinRequestDto) {
        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage(), userService.userSignup(userJoinRequestDto));

        return ResponseEntity.ok(res);
    }

    @GetMapping("/user/check-email")
    public ResponseEntity<ApiResponseDto> checkDuplicationEmail(@RequestParam String email) {
        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage(), userService.checkDuplicationEmail(email));

        return ResponseEntity.ok(res);
    }
    /*
      USER, ADMIN 권한 모두 호출할 수 있도록 설정
    */
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponseDto> getMyUserInfo() {
        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage(), userService.getMyUserWithRoles().get());

        return ResponseEntity.ok(res);
    }

    /*
       ADMIN 권한만 호출할 수 있도록 설정
     */
    @GetMapping(value = "/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDto> getUserInfo(@PathVariable("email") String email) {
        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage(), userService.getUserWithRoles(email).get());

        return ResponseEntity.ok(res);
    }
}
