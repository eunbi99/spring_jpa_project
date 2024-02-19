package com.europehang.europe.post.dto;

import com.europehang.europe.category.domain.Category;
import com.europehang.europe.common.enums.Gender;
import com.europehang.europe.post.domain.Post;
import com.europehang.europe.user.domain.User;
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
public class PostRegisterRequestDto {
    @NotBlank(message = "제목은 필수 입력값 입니다.")
    private String title;

    @NotNull(message = "성별은 필수 입력값 입니다.")
    private Gender gender;

    @NotBlank(message = "내용은 필수 입력값 입니다.")
    private String content;

    private String kakao_url;

    private Long parentCategoryId;

    private Long childCategoryId;

    private int recruitmentLimit;

    private String travelStartDate;


    public Post toEntity(Category parentCategory, Category childCategory, User user) {
        return Post.builder()
                .title(title)
                .gender(gender)
                .content(content)
                .kakao_url(kakao_url)
                .recruitmentLimit(recruitmentLimit)
                .parentCategory(parentCategory)
                .childCategory(childCategory)
                .travelDate(travelStartDate)
                .user(user)
                .build();
    }
}
