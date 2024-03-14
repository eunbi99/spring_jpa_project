package com.europehang.europe.user.controller;

import com.europehang.europe.common.dto.ApiResponse;
import com.europehang.europe.common.dto.ErrorResponse;
import com.europehang.europe.common.enums.ResponseStatus;
import com.europehang.europe.jwt.JwtFilter;
import com.europehang.europe.jwt.JwtTokenProvider;
import com.europehang.europe.jwt.dto.TokenDto;
import com.europehang.europe.user.dto.UserJoinRequestDto;
import com.europehang.europe.user.dto.UserLoginDto;
import com.europehang.europe.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Tag(name = "user", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/email/{email}")
    @Operation(summary = "이메일 중복확인", description = "이메일 중복확인을 한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "이메일 중복 여부(true/false)", content = @Content(schema = @Schema(implementation = ApiResponse.class)))})
    public ResponseEntity<ApiResponse> checkDuplicationEmail(@PathVariable(name = "email") String email) {
        boolean isExistEmail = userService.checkDuplicationEmail(email);
        String emailExistStatus = isExistEmail ? "Y" : "N";

        ApiResponse res = ApiResponse.of(ResponseStatus.OK, emailExistStatus);

        return ResponseEntity.ok(res);
    }
    @GetMapping("/user/nickname/{nickname}")
    @Operation(summary = "닉네임 중복확인", description = "닉네임 중복확인을 한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "닉네임 중복 여부(true/false)", content = @Content(schema = @Schema(implementation = ApiResponse.class)))})
    public ResponseEntity<ApiResponse> checkDuplicationNickname(@PathVariable(name = "nickname")  String nickname) {
        boolean isExistNickname = userService.checkDuplicationNickname(nickname);
        String nicknameExistStatus = isExistNickname ? "Y" : "N";

        ApiResponse res = ApiResponse.of(ResponseStatus.OK, nicknameExistStatus);

        return ResponseEntity.ok(res);
    }
    /*
      USER, ADMIN 권한 모두 호출할 수 있도록 설정
    */
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "User와 Admin 권한 조회", description = "USER, ADMIN 권한 모두 호출")
    public ResponseEntity<ApiResponse> getMyUserInfo() {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, userService.getMyUserWithRoles().get());

        return ResponseEntity.ok(res);
    }

    /*
       ADMIN 권한만 호출할 수 있도록 설정
     */
    @GetMapping(value = "/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Admin 권한 조회", description = "ADMIN 권한만 호출할 수 있도록 설정")
    public ResponseEntity<ApiResponse> getUserInfo(@PathVariable("email") String email) {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, userService.getUserWithRoles(email).get());

        return ResponseEntity.ok(res);
    }
}
