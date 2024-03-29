package com.europehang.europe.post.domain;

import com.europehang.europe.category.domain.Category;
import com.europehang.europe.common.BaseEntity;
import com.europehang.europe.common.converts.GenderConvert;
import com.europehang.europe.common.converts.RecruitStatusConvert;
import com.europehang.europe.common.enums.Gender;
import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.post.dto.PostModifyRequestDto;
import com.europehang.europe.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Convert(converter = GenderConvert.class)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String kakao_url;

    @Column(name = "recruitment_limit")
    private int recruitmentLimit;

    @Column(name = "is_recruit_completed")
    @ColumnDefault("N")
    @Convert(converter = RecruitStatusConvert.class)
    private RecruitStatus recruitStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_category_id")
    private Category childCategory;

    @Column(name="travel_start_date")
    private String travelStartDate;

    @ColumnDefault("0")
    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<PostLike>();




    @Builder
    public Post(String title, Gender gender, String content, String kakao_url, int recruitmentLimit, Category parentCategory, Category childCategory, String travelStartDate, RecruitStatus recruitStatus, int views, User user){
        this.title= title;
        this.gender = gender;
        this.content = content;
        this.kakao_url = kakao_url;
        this.recruitmentLimit = recruitmentLimit;
        this.parentCategory = parentCategory;
        this.childCategory = childCategory;
        this.travelStartDate = travelStartDate;
        this.recruitStatus = recruitStatus;
        this.views = views;
        this.user = user;

    }

    public void update(PostModifyRequestDto modifyRequestDto, Category parentCategory, Category childCategory)  {
        this.title = modifyRequestDto.getTitle();
        this.gender = modifyRequestDto.getGender();
        this.content = modifyRequestDto.getContent();
        this.kakao_url = modifyRequestDto.getKakao_url();
        this.parentCategory = parentCategory;
        this.childCategory = childCategory;
        this.recruitmentLimit = modifyRequestDto.getRecruitmentLimit();
        this.travelStartDate = modifyRequestDto.getTravelStartDate();
        this.recruitStatus = modifyRequestDto.getRecruitStatus();
    }

}
