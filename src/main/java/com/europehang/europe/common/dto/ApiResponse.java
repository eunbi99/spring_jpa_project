package com.europehang.europe.common.dto;

import com.europehang.europe.common.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse {
    private int code;
    private String message;
    private Object data;


    public static ApiResponse of(ResponseStatus status, Object data) {
        return ApiResponse.builder()
                .code(status.getCode())
                .message(status.getMessage())
                .data(data)
                .build();
    }

    public static ApiResponse of(ResponseStatus status) {
        return ApiResponse.builder()
                .code(status.getCode())
                .message(status.getMessage())
                .build();
    }


}
