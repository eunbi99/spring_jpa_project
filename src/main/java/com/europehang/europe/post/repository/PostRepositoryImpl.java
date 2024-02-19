package com.europehang.europe.post.repository;

import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.post.domain.Post;
import com.europehang.europe.post.dto.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

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
    public List<PostListResponseDto> searchPostByCondition(PostSearchCondition condition) {
         return queryFactory
             .select(new QPostListResponseDto(
                         post.id,
                         post.title,
                         post.content,
                         post.isRecruitCompleted,
                         post.createdDate,
                         post.likes,
                         post.parentCategory.categoryName.as("country"),
                         post.childCategory.categoryName.as("city"),
                         post.user.nickname
                 ))
                 .from(post)
                 .join(post.user)
                 .join(post.parentCategory)
                 .join(post.childCategory)
                 .where(
                         parentCategoryEq(condition.getParentCategoryId()),
                         childCategoryEq(condition.getChildCategoryId()),
                         isRecruitCompleted(condition.getIsRecruitCompleted())
                 )
                 .fetch();
    }

    @Override
    public PostDetailResponseDto getPostDetailById(Long id) {
        return queryFactory
                .select(new QPostDetailResponseDto(
                        post.title,
                        post.gender,
                        post.content,
                        post.kakao_url,
                        post.recruitmentLimit,
                        post.isRecruitCompleted,
                        post.likes,
                        post.createdDate,
                        post.travelDate,
                        post.parentCategory.categoryName.as("country"),
                        post.childCategory.categoryName.as("city"),
                        post.views,
                        post.user.nickname
                ))
                .from(post)
                .join(post.user)
                .where(post.id.eq(id))
                .fetchOne();
    }

    @Override
    public Slice<PostListResponseDto> getPostListWithPaging(Pageable pageable) {
        List<PostListResponseDto> posts = queryFactory
                .select(new QPostListResponseDto(
                        post.id,
                        post.title,
                        post.content,
                        post.isRecruitCompleted,
                        post.createdDate,
                        post.likes,
                        post.parentCategory.categoryName.as("country"),
                        post.childCategory.categoryName.as("city"),
                        post.user.nickname
                )).from(post)
                .join(post.user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        return new SliceImpl<>(posts, pageable, hasNextPage(posts, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<PostListResponseDto> posts, int pageSize) {
        if(posts.size() > pageSize) {
            posts.remove(pageSize);
        }
        return false;
    }
}
