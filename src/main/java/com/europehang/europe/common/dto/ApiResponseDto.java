package com.europehang.europe.common.dto;

import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.common.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponseDto {
    private ResponseStatus status;
    private String message;
    private Object data;


    public ApiResponseDto() {
        this.status = ResponseStatus.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }

    public static ApiResponseDto of(ResponseStatus status, String message, Object data) {
        return ApiResponseDto.builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponseDto of(ResponseStatus status, String message) {
        return ApiResponseDto.builder()
                .status(status)
                .message(message)
                .build();
    }


}
