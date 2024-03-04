package com.europehang.europe.post.service;

import com.europehang.europe.category.domain.Category;
import com.europehang.europe.category.repository.CategoryRepository;
import com.europehang.europe.exception.CustomException;
import com.europehang.europe.post.domain.Post;
import com.europehang.europe.post.dto.*;
import com.europehang.europe.post.repository.PostRepository;
import com.europehang.europe.user.domain.User;
import com.europehang.europe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.europehang.europe.common.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long savePost(PostRegisterRequestDto postRequestDto, String username) {
        Category parentCategory = vaildCategoryId(postRequestDto.getParentCategoryId());
        Category childCategory = vaildCategoryId(postRequestDto.getChildCategoryId());

        User user = userRepository.findAllByEmail(username)
                .orElseThrow(()-> new CustomException(USER_NOT_FOUND));

        Post post = postRequestDto.toEntity(parentCategory,childCategory,user);

        postRepository.save(post);
        return post.getId();
    }

    /**
     * 게시글 리스트 페이징
     * @return
     */
    public Slice<PostListResponseDto> getPostListWithPaging(Long postId, Pageable pageable) {

        return postRepository.getPostListWithPaging(postId, pageable);
    }

    /**
     * 글 번호로 조회
     */
    public PostDetailResponseDto findPostById(Long id) {
       return postRepository.getPostDetailById(id);
    }

    /**
     * 조건으로 게시글 리스트 조회
     */

    public Slice<PostListResponseDto> getPostListByCondition(Long postId, Pageable pageable, PostSearchCondition condition) {
        return postRepository.searchPostByCondition(postId,pageable,condition);
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

    public boolean isExistPost(Long id) {
        return postRepository.existsById(id);
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
    }


}
