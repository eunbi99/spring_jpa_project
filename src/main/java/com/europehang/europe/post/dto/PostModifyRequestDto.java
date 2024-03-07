package com.europehang.europe.post.dto;

import com.europehang.europe.category.domain.Category;
import com.europehang.europe.common.enums.Gender;
import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.common.util.SchemaDescriptionUtils;
import com.europehang.europe.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostModifyRequestDto {
    @NotBlank(message = "제목은 필수 입력값 입니다.")
    @Schema(description = SchemaDescriptionUtils.Post.title, example = "동행 구해요")
    private String title;

    @NotNull(message = "성별은 필수 입력값 입니다.")
    @Schema(description = SchemaDescriptionUtils.User.gender, allowableValues = {"FEMALE", "MALE"})
    private Gender gender;

    @NotBlank(message = "내용은 필수 입력값 입니다.")
    @Schema(description = SchemaDescriptionUtils.Post.content, example = "내일 동행 구합니다")
    private String content;

    @Schema(description = SchemaDescriptionUtils.Post.kakao_url, example = "https://open.kakao.com/test")
    private String kakao_url;

    @Schema(description = SchemaDescriptionUtils.Post.parentCategoryId, example = "1")
    private Long parentCategoryId;

    @Schema(description = SchemaDescriptionUtils.Post.childCategoryId, example = "6")
    private Long childCategoryId;

    @Schema(description = SchemaDescriptionUtils.Post.recruitmentLimit, example = "4")
    private int recruitmentLimit;

    @Schema(description = SchemaDescriptionUtils.Post.travelStartDate, example = "2024-03-13")
    private String travelStartDate;

    @Schema(description = SchemaDescriptionUtils.Post.recruitStatus, allowableValues = {"RECRUITING", "RECRUIT_COMPLETE"})
    private RecruitStatus recruitStatus;


    public Post toEntity(Category parentCategory, Category childCategory) {
        return Post.builder()
                .title(title)
                .gender(gender)
                .content(content)
                .kakao_url(kakao_url)
                .recruitmentLimit(recruitmentLimit)
                .parentCategory(parentCategory)
                .childCategory(childCategory)
                .travelStartDate(travelStartDate)
                .recruitStatus(recruitStatus)
                .build();
    }
}
