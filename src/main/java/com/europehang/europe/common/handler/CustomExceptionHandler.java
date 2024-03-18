package com.europehang.europe.common.handler;

import com.europehang.europe.common.dto.ErrorResponse;
import com.europehang.europe.exception.CustomException;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handlerCustomException(CustomException exception) {
        return ErrorResponse.toResponseEntity(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handlerCustomException(MethodArgumentNotValidException exception) {
        return ErrorResponse.toResponseEntity(exception);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        return ErrorResponse.toResponseEntity(exception);
    }
}
