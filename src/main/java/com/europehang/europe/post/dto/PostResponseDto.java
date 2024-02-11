package com.europehang.europe.post.dto;

import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.post.domain.Post;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private RecruitStatus isRecrutingYn;
    private LocalDateTime createdDate;
    private int likes;
    private String country;
    private String city;

    @QueryProjection
    public PostResponseDto(Long id, String title, String content, RecruitStatus isRecrutingYn, LocalDateTime createdDate, int likes, String country, String city) {
        this.id= id;
        this.title = title;
        this.content = content;
        this.isRecrutingYn = isRecrutingYn;
        this.createdDate = createdDate;
        this.likes = likes;
        this.country = country;
        this.city = city;
    }

    public static PostResponseDto toDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .isRecrutingYn(post.getIsRecruitCompleted())
                .likes(post.getLikes())
                .createdDate(post.getCreatedDate())
                .country(post.getParentCategory().getCategoryName())
                .city(post.getChildCategory().getCategoryName())
                .build();
    }

}
