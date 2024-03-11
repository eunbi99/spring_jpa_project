package com.europehang.europe.auth.controller;

import com.europehang.europe.auth.service.AuthService;
import com.europehang.europe.common.dto.ApiResponse;
import com.europehang.europe.common.dto.ErrorResponse;
import com.europehang.europe.common.enums.ResponseStatus;
import com.europehang.europe.jwt.JwtFilter;
import com.europehang.europe.jwt.dto.TokenDto;
import com.europehang.europe.user.dto.UserJoinRequestDto;
import com.europehang.europe.user.dto.UserLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입 및 JWT 관련 ", description = "JWT 관련, 회원가입 API")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signUp")
    @Operation(summary = "회원가입", description = "회원가입을 한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 등록 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid UserJoinRequestDto userJoinRequestDto) {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, authService.userSignup(userJoinRequestDto));

        return ResponseEntity.ok(res);
    }

    @Operation(summary = "JWT 로그인", description = "JWT를 사용하여 로그인을 한다.")
    @PostMapping("/signIn")
    public ResponseEntity<TokenDto> signin(@Valid @RequestBody UserLoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword());

        TokenDto tokenDto = authService.signin(authenticationToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer" + tokenDto.getAccessToken());

        return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/token/reissue")
    @Operation(summary = "JWT 액세스 토큰 재발급", description = "액세스 토큰 만료 시 리프레쉬 토큰을 검증하여 액세스 토큰을 재발급한다.")
    public ResponseEntity<TokenDto> reissueAccessToken(@RequestHeader(value="REFRESH-TOKEN") String refreshToken) {
        TokenDto tokenDto = authService.reissueAccessToken(refreshToken);

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

}
