package com.europehang.europe.post.repository;

import com.europehang.europe.post.dto.PostDetailResponseDto;
import com.europehang.europe.post.dto.PostListResponseDto;
import com.europehang.europe.post.dto.PostSearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {
    /**
     * 동적 쿼리 - 조건 별 조회 ( 날짜, 카테고리, 인원, ..)
     *
     */
    Slice<PostListResponseDto> searchPostByCondition(Long postId, Pageable pageable, PostSearchCondition condition);

    PostDetailResponseDto getPostDetailById(Long id);

    Slice<PostListResponseDto> getPostListWithPaging(Long id, Pageable pageable);
}
