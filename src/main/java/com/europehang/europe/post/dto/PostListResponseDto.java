package com.europehang.europe.post.dto;

import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.post.domain.Post;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
public class PostListResponseDto {
    private Long id;
    private String title;
    private String content;
    private RecruitStatus RecrutingStatus;
    private LocalDateTime createdDate;
    private int likes;
    private String country;
    private String city;
    private String nickname;
    public String travelStartDate;

    @QueryProjection
    public PostListResponseDto(Long id, String title, String content, RecruitStatus RecrutingStatus, LocalDateTime createdDate, int likes, String country, String city, String nickname,String travelStartDate) {
        this.id= id;
        this.title = title;
        this.content = content;
        this.RecrutingStatus = RecrutingStatus;
        this.createdDate = createdDate;
        this.likes = likes;
        this.country = country;
        this.city = city;
        this.nickname = nickname;
        this.travelStartDate = travelStartDate;
    }

    public static PostListResponseDto toDto(Post post) {
        return PostListResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .RecrutingStatus(post.getIsRecruitCompleted())
                .likes(post.getPostLikes().size())
                .createdDate(post.getCreatedDate())
                .country(post.getParentCategory().getCategoryName())
                .city(post.getChildCategory().getCategoryName())
                .nickname(post.getUser().getNickname())
                .travelStartDate(post.getTravelDate())
                .build();
    }

}
