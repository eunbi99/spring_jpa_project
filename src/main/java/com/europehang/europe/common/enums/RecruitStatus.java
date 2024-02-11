package com.europehang.europe.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
public enum RecruitStatus {
    RECRUIT_COMPLETE("Y"),
    RECRUITING("N");

    private String status;
    RecruitStatus(String status) {
        this.status = status;
    }

    public static RecruitStatus fromCode(String dbData) {
        return Arrays.stream(RecruitStatus.values())
                .filter(v-> v.getStatus().equals(dbData))
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException(String.format("존재하지 않는 상태 코드입니다. code: %s", dbData)));
    }
}
