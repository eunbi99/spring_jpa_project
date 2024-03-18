package com.europehang.europe.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SUCCESS(HttpStatus.OK, "정상 처리 되었습니다."),

    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 카테고리를 찾을 수 없습니다."),
    CATEGORY_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"이미 존재하는 카테고리입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 유저를 찾을 수 없습니다."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"이미 존재하는 회원입니다."),
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 게시글을 찾을 수 없습니다."),

    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.BAD_REQUEST,"유효하지 않은 Bearer 인증 입니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.BAD_REQUEST,"인증 정보가 존재하지 않습니다."),
    MISMATCH_TOKEN(HttpStatus.BAD_REQUEST,"동일하지 않은 토큰입니다."),
    INVALID_SIGNATURE(HttpStatus.BAD_REQUEST,"잘못된 JWT 서명입니다."),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST,"만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST,"지원되지 않는 JWT 토큰입니다."),
    MALFORMED_TOKEN(HttpStatus.BAD_REQUEST,"JWT 토큰이 잘못되었습니다.");


    private final HttpStatus status;
    private final String message;
}
