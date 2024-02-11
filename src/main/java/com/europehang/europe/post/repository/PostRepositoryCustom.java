package com.europehang.europe.post.repository;

import com.europehang.europe.post.dto.PostResponseDto;
import com.europehang.europe.post.dto.PostSearchCondition;

import java.util.List;

public interface PostRepositoryCustom {
    /**
     * 동적 쿼리 - 조건 별 조회 ( 날짜, 카테고리, 인원, ..)
     *
     */
    List<PostResponseDto> searchPostByCondition(PostSearchCondition condition);
}
