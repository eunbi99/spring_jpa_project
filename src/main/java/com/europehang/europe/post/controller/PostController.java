package com.europehang.europe.post.controller;

import com.europehang.europe.common.dto.ApiResponseDto;
import com.europehang.europe.common.enums.ResponseStatus;
import com.europehang.europe.post.dto.PostModifyRequestDto;
import com.europehang.europe.post.dto.PostRegisterRequestDto;
import com.europehang.europe.post.dto.PostSearchCondition;
import com.europehang.europe.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<ApiResponseDto> savePost(@RequestBody PostRegisterRequestDto postRequestDto, Authentication authentication) {
        String username = authentication.getName();
        postService.savePost(postRequestDto,username);

        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage());
        return ResponseEntity.ok(res);
    }

    /**
     * 모든 게시글 조회
     * @return
     */

    @GetMapping("/posts")
    public ResponseEntity<ApiResponseDto> getPostList(@RequestParam(value = "id", required = false)  Long id,
                                                      @PageableDefault(size = 6,direction = Sort.Direction.DESC) Pageable pageable) {
        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage(), postService.getPostListWithPaging(id, pageable));
        return ResponseEntity.ok(res);
    }

    /**
     * 글 번호로 조회
     */
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> findPostById(@PathVariable("postId") Long id) {
        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage(),postService.findPostById(id));
        return ResponseEntity.ok(res);
    }
    /**
     * 조건으로 게시글 리스트 조회
     */
    @GetMapping("/posts/search")
    public ResponseEntity<ApiResponseDto> getPostListByCondition(@RequestParam(value = "id", required = false)  Long id,
                                                                 @PageableDefault(size = 6,direction = Sort.Direction.DESC) Pageable pageable,
                                                                 @RequestBody PostSearchCondition condition) {
        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage(),postService.getPostListByCondition(id,pageable,condition));
        return ResponseEntity.ok(res);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> modifyPost(@PathVariable("postId") Long id, @RequestBody PostModifyRequestDto modifyRequestDto) {
        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage(),postService.modifyPost(id,modifyRequestDto));
        return ResponseEntity.ok(res);
    }

    /**
     * 게시글 삭제
     * @param id
     */
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable("postId") Long id) {
        postService.deletePost(id);
        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage());
        return ResponseEntity.ok(res);
    }

}
