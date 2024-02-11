package com.europehang.europe.post.dto;

import com.europehang.europe.common.enums.Gender;
import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponseDto {
    public String title;
    public Gender gender;
    public String content;
    public String kakao_url;
    public int recruitmentLimit;
    public RecruitStatus isRecruitCompleted;
    public int likes;
    public LocalDateTime createdDate;
    public String travelStartDate;
    public String country;
    public String city;
    public int views;

    public static PostDetailResponseDto toDto(Post post) {
        return PostDetailResponseDto.builder()
                .title(post.getTitle())
                .gender(post.getGender())
                .content(post.getContent())
                .kakao_url(post.getKakao_url())
                .isRecruitCompleted(post.getIsRecruitCompleted())
                .likes(post.getLikes())
                .createdDate(post.getCreatedDate())
                .travelStartDate(post.getTravelDate())
                .country(post.getParentCategory().getCategoryName())
                .city(post.getChildCategory().getCategoryName())
                .views(post.getViews())
                .build();
        
      

    }
}
