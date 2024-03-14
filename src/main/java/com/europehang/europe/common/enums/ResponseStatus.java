package com.europehang.europe.common.enums;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    OK(200, "성공적으로 처리되었습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),
    NOT_FOUND(404, "요청한 자원을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "서버에서 오류가 발생했습니다.");

    private int code;
    private String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
