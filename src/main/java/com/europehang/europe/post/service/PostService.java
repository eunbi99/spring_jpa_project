package com.europehang.europe.post.service;

import com.europehang.europe.category.domain.Category;
import com.europehang.europe.category.repository.CategoryRepository;
import com.europehang.europe.exception.CustomException;
import com.europehang.europe.post.domain.Post;
import com.europehang.europe.post.dto.*;
import com.europehang.europe.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.europehang.europe.exception.ErrorCode.CATEGORY_NOT_FOUND;
import static com.europehang.europe.exception.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long savePost(PostRegisterRequestDto postRequestDto) {

        Category parentCategory = vaildCategoryId(postRequestDto.getParentCategoryId());
        Category childCategory = vaildCategoryId(postRequestDto.getChildCategoryId());

        Post post = postRequestDto.toEntity(parentCategory,childCategory);

        postRepository.save(post);
        return post.getId();
    }

    /**
     * 모든 게시글 조회
     * @return
     */
    public List<PostResponseDto> getAllPost() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 글 번호로 조회
     */
    public PostDetailResponseDto findPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        return PostDetailResponseDto.toDto(post);
    }
    /**
     * 조건으로 게시글 리스트 조회
     */

    public List<PostResponseDto> getPostListByCondition(PostSearchCondition condition) {
        return postRepository.searchPostByCondition(condition);
    }

    /**
     * 게시글 수정
     */

    @Transactional
    public Long modifyPost(Long id,PostModifyRequestDto modifyRequestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->new CustomException(POST_NOT_FOUND));

        Category parentCategory = vaildCategoryId(modifyRequestDto.getParentCategoryId());
        Category childCategory = vaildCategoryId(modifyRequestDto.getChildCategoryId());


        post.update(modifyRequestDto, parentCategory,childCategory);

        return post.getId();
    }

    /**
     * 게시글 삭제
     * @param id
     */
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        postRepository.delete(post);
    }

    private Category vaildCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));
    }


}
