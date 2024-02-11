package com.europehang.europe.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
public enum Gender {
    MALE("M"),
    FEMALE("F");

    private String code;

    Gender(String code) {
        this.code = code;
    }

    public static Gender fromCode(String dbData) {
        return Arrays.stream(Gender.values())
                .filter(v-> v.getCode().equals(dbData))
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException(String.format("존재하지 않는 성별 코드입니다. code: %s", dbData)));
    }
}
