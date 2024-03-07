package com.europehang.europe.user.controller;

import com.europehang.europe.common.dto.ApiResponse;
import com.europehang.europe.common.dto.ErrorResponse;
import com.europehang.europe.common.enums.ResponseStatus;
import com.europehang.europe.user.dto.UserJoinRequestDto;
import com.europehang.europe.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    @Operation(summary = "회원가입", description = "회원가입을 한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 등록 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid UserJoinRequestDto userJoinRequestDto) {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, userService.userSignup(userJoinRequestDto));

        return ResponseEntity.ok(res);
    }

    @GetMapping("/user/check-email")
    @Operation(summary = "이메일 중복확인", description = "이메일 중복확인을 한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "이메일 중복 여부(true/false)", content = @Content(schema = @Schema(implementation = ApiResponse.class)))})
    public ResponseEntity<ApiResponse> checkDuplicationEmail(@RequestParam String email) {
        boolean isExistEmail = userService.checkDuplicationEmail(email);
        String emailExistStatus = isExistEmail ? "Y" : "N";

        ApiResponse res = ApiResponse.of(ResponseStatus.OK, emailExistStatus);

        return ResponseEntity.ok(res);
    }
    /*
      USER, ADMIN 권한 모두 호출할 수 있도록 설정
    */
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse> getMyUserInfo() {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, userService.getMyUserWithRoles().get());

        return ResponseEntity.ok(res);
    }

    /*
       ADMIN 권한만 호출할 수 있도록 설정
     */
    @GetMapping(value = "/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getUserInfo(@PathVariable("email") String email) {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, userService.getUserWithRoles(email).get());

        return ResponseEntity.ok(res);
    }
}
