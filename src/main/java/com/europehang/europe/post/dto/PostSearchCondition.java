package com.europehang.europe.post.dto;


import com.europehang.europe.common.enums.RecruitStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSearchCondition {
    // 부모 카테고리
    private Long parentCategoryId;
    // 자식 카테고리
    private Long childCategoryId;
    // 날짜
    private String travelDate;
    // 모집 중
    private RecruitStatus isRecruitCompleted;
}
