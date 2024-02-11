package com.europehang.europe.post.repository;

import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.post.dto.PostResponseDto;
import com.europehang.europe.post.dto.PostSearchCondition;
import com.europehang.europe.post.dto.QPostResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;

import static com.europehang.europe.post.domain.QPost.post;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    /**
     * 동적 쿼리 - 조건 별 조회 ( 날짜, 카테고리, 인원, ..)
     *
     */
    private BooleanExpression parentCategoryEq(Long parentCategoryId) {
        if(parentCategoryId == null) {
            return null;
        }
        return post.parentCategory.id.eq(parentCategoryId);
    }

    private BooleanExpression childCategoryEq(Long childCategoryId) {
        if(childCategoryId == null) {
            return null;
        }
        return post.childCategory.id.eq(childCategoryId);
    }

    private BooleanExpression isRecruitCompleted(RecruitStatus recruitStatus) {
        if (recruitStatus == null) {
            return null;
        }
        return post.isRecruitCompleted.stringValue().eq(recruitStatus.getStatus());
    }

    @Override
    public List<PostResponseDto> searchPostByCondition(PostSearchCondition condition) {
         return queryFactory
             .select(new QPostResponseDto(
                         post.id,
                         post.title,
                         post.content,
                         post.isRecruitCompleted,
                         post.createdDate,
                         post.likes,
                         post.parentCategory.categoryName.as("country"),
                         post.childCategory.categoryName.as("city")
                 ))
                 .from(post)
                 .join(post.parentCategory)
                 .join(post.childCategory)
                 .where(
                         parentCategoryEq(condition.getParentCategoryId()),
                         childCategoryEq(condition.getChildCategoryId()),
                         isRecruitCompleted(condition.getIsRecruitCompleted())
                 )
                 .fetch();
    }
}
