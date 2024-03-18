package com.europehang.europe.common.dto;

import com.europehang.europe.exception.CustomException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponse {
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    public static ResponseEntity<ErrorResponse> toResponseEntity(CustomException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorResponse.builder()
                        .status(e.getErrorCode().getStatus().value())
                        .message(e.getErrorCode().getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message("이메일 또는 비밀번호가 맞지 않습니다.")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}
