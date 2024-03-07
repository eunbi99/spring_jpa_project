package com.europehang.europe.post.dto;

import com.europehang.europe.common.enums.Gender;
import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.common.util.SchemaDescriptionUtils;
import com.europehang.europe.post.domain.Post;
import com.europehang.europe.user.domain.User;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
public class PostDetailResponseDto {
    private String title;
    public Gender gender;
    public String content;
    public String kakao_url;
    public int recruitmentLimit;
    public RecruitStatus recruitStatus;
    public int likes;
    public LocalDateTime createdDate;
    public String travelStartDate;
    public String country;
    public String city;
    public int views;
    public String nickname;

    @QueryProjection
    public PostDetailResponseDto(String title, Gender gender, String content, String kakao_url, int recruitmentLimit, RecruitStatus recruitStatus, int likes, LocalDateTime createdDate, String travelStartDate, String country, String city, int views, String nickname) {
        this.title = title;
        this.gender = gender;
        this.content = content;
        this.kakao_url = kakao_url;
        this.recruitmentLimit = recruitmentLimit;
        this.recruitStatus = recruitStatus;
        this.likes = likes;
        this.createdDate = createdDate;
        this.travelStartDate = travelStartDate;
        this.country = country;
        this.city = city;
        this.views = views;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "PostDetailResponseDto{" +
                "title='" + title + '\'' +
                ", gender=" + gender +
                ", content='" + content + '\'' +
                ", kakao_url='" + kakao_url + '\'' +
                ", recruitmentLimit=" + recruitmentLimit +
                ", recruitStatus=" + recruitStatus +
                ", likes=" + likes +
                ", createdDate=" + createdDate +
                ", travelStartDate='" + travelStartDate + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", views=" + views +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
