package com.europehang.europe.post.dto;


import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.common.util.SchemaDescriptionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSearchCondition {
    @Schema(description = SchemaDescriptionUtils.Post.parentCategoryId, example = "1")
    private Long parentCategoryId;

    @Schema(description = SchemaDescriptionUtils.Post.childCategoryId, example = "6")
    private Long childCategoryId;

    @Schema(description = SchemaDescriptionUtils.Post.travelStartDate, example = "2024-03-17")
    private String travelStartDate;

    @Schema(description = SchemaDescriptionUtils.Post.recruitStatus, allowableValues = {"RECRUITING", "RECRUIT_COMPLETE"})
    private RecruitStatus recruitStatus;
}
