package com.europehang.europe.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("회원"),
    ADMIN("관리자");

    private final String role;
}
