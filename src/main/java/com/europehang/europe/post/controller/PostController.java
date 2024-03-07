package com.europehang.europe.post.controller;

import com.europehang.europe.common.dto.ApiResponse;
import com.europehang.europe.common.dto.ErrorResponse;
import com.europehang.europe.common.enums.ResponseStatus;
import com.europehang.europe.post.dto.*;
import com.europehang.europe.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "post", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    @Operation(summary = "게시글 등록", description = "게시글 등록을 한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 등록 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<ApiResponse> savePost(@RequestBody @Valid PostRegisterRequestDto postRequestDto, Authentication authentication) {
        String username = authentication.getName();
        postService.savePost(postRequestDto,username);

        ApiResponse res = ApiResponse.of(ResponseStatus.OK);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/posts")
    @Operation(summary = "게시글 리스트 조회", description = "게시글을 6개씩 페이징해서 리스트를 조회한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PostListResponseDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<ApiResponse> getPostList(@RequestParam(value = "id", required = false)  Long id,
                                                   @PageableDefault(size = 6,direction = Sort.Direction.DESC) Pageable pageable) {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, postService.getPostListWithPaging(id, pageable));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 상세 조회", description = "게시글 번호로 상세조회 한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PostDetailResponseDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<ApiResponse> findPostById(@PathVariable("postId") Long id) {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, postService.findPostById(id));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/posts/search")
    @Operation(summary = "게시글 조건 조회", description = "게시글을 조건을 통해 조회 한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PostListResponseDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<ApiResponse> getPostListByCondition(@RequestParam(value = "id", required = false)  Long id,
                                                              @PageableDefault(size = 6,direction = Sort.Direction.DESC) Pageable pageable,
                                                              @RequestBody PostSearchCondition condition) {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, postService.getPostListByCondition(id,pageable,condition));
        return ResponseEntity.ok(res);
    }

    @PutMapping("/posts/{postId}")
    @Operation(summary = "게시글 수정", description = "게시글을 수정한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<ApiResponse> modifyPost(@PathVariable("postId") Long id, @RequestBody @Valid PostModifyRequestDto modifyRequestDto) {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, postService.modifyPost(id,modifyRequestDto));
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/posts/{postId}")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Long id) {
        postService.deletePost(id);
        ApiResponse res = ApiResponse.of(ResponseStatus.OK);
        return ResponseEntity.ok(res);
    }

}
