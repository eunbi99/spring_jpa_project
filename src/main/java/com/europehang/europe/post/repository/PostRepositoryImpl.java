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

    private BooleanExpression recruitStatusEq(RecruitStatus recruitStatus) {
        if (recruitStatus == null) {
            return null;
        }
        return post.recruitStatus.stringValue().eq(recruitStatus.getStatus());
    }

    @Override
    public Slice<PostListResponseDto> searchPostByCondition(Long postId, Pageable pageable, PostSearchCondition condition) {
        List<PostListResponseDto> posts = queryFactory
                 .select(new QPostListResponseDto(
                             post.id,
                             post.title,
                             post.content,
                             post.recruitStatus,
                             post.createdDate,
                             post.postLikes.size(),
                             post.parentCategory.categoryName.as("country"),
                             post.childCategory.categoryName.as("city"),
                             post.user.nickname,
                             post.travelStartDate
                     ))
                     .from(post)
                     .join(post.user)
                     .join(post.parentCategory)
                     .join(post.childCategory)
                     .where(
                             ltPostId(postId),
                             parentCategoryEq(condition.getParentCategoryId()),
                             childCategoryEq(condition.getChildCategoryId()),
                             recruitStatusEq(condition.getRecruitStatus())
                     )
                     .limit(pageable.getPageSize()+1)
                     .orderBy(post.id.desc())
                     .fetch();

        return new SliceImpl<>(posts, pageable, hasNextPage(posts, pageable.getPageSize()));
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
                        post.recruitStatus,
                        post.postLikes.size(),
                        post.createdDate,
                        post.travelStartDate,
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
    public Slice<PostListResponseDto> getPostListWithPaging(Long postId, Pageable pageable) {
        List<PostListResponseDto> posts = queryFactory
                .select(new QPostListResponseDto(
                        post.id,
                        post.title,
                        post.content,
                        post.recruitStatus,
                        post.createdDate,
                        post.postLikes.size(),
                        post.parentCategory.categoryName.as("country"),
                        post.childCategory.categoryName.as("city"),
                        post.user.nickname,
                        post.travelStartDate
                )).from(post)
                .join(post.user)
                .where(
                        ltPostId(postId)
                )
                .limit(pageable.getPageSize()+1)
                .orderBy(post.id.desc())
                .fetch();

        return new SliceImpl<>(posts, pageable, hasNextPage(posts, pageable.getPageSize()));
    }

    private BooleanExpression ltPostId(Long postId) {
        if (postId == null) {
            return null;
        }

        return post.id.lt(postId);
    }

    private boolean hasNextPage(List<PostListResponseDto> posts, int pageSize) {
        if(posts.size() > pageSize) {
            posts.remove(pageSize);
            return true;
        }
        return false;
    }
}
