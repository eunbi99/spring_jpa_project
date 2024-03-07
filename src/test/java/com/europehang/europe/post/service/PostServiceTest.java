package com.europehang.europe.post.service;

import com.europehang.europe.common.enums.Gender;
import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.exception.CustomException;
import com.europehang.europe.post.dto.*;
import com.europehang.europe.post.repository.PostLikeRepository;
import com.europehang.europe.user.domain.User;
import com.europehang.europe.user.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    private static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void 게시글_등록() {
        String username = "bambi@gmail.com";
        PostRegisterRequestDto postRequest = PostRegisterRequestDto.builder()
                .title("동행구해용")
                .gender(Gender.MALE)
                .content("동행테스트입니다")
                .kakao_url("kakao_url")
                .recruitmentLimit(4)
                .travelStartDate("2024-02-28")
                .parentCategoryId(1L)
                .childCategoryId(8L)
                .build();

        Long id = postService.savePost(postRequest,username);

        Assertions.assertEquals(id,31);

    }

    @Test
    public void 게시글등록_필수입력값검증() {
        PostRegisterRequestDto postRequest = PostRegisterRequestDto.builder()
                .title("")
                .content("")
                .kakao_url("kakao_url")
                .recruitmentLimit(4)
                .travelStartDate("2024-02-28")
                .parentCategoryId(1L)
                .childCategoryId(8L)
                .build();

        Set<ConstraintViolation<PostRegisterRequestDto>> violations = validator.validate(postRequest);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertEquals(violations.size(),3);

    }

    @Test
    public void 없는_카테고리_게시글_등록() {
        String username = "bambi@gmail.com";
        PostRegisterRequestDto postRequest = PostRegisterRequestDto.builder()
                .title("파리 개선문 동행 구해요")
                .gender(Gender.FEMALE)
                .content("개선문 가고싶어요")
                .kakao_url("kakao_url")
                .recruitmentLimit(2)
                .travelStartDate("2024-02-28")
                .parentCategoryId(55L)
                .childCategoryId(40L)
                .build();

        assertThrows(CustomException.class, () -> postService.savePost(postRequest,username));

    }

    @Test
    public void 모든_게시글_조회() {
        Long postId= 6L;
        Pageable pageable = PageRequest.ofSize(6);
        Slice<PostListResponseDto> posts = postService.getPostListWithPaging(postId, pageable);

        for(PostListResponseDto post : posts) {
            System.out.println("글 번호 : " + post.getId()+ " 글 제목 : " + post.getTitle() + " 내용 : " + post.getContent() + "나라 : " + post.getCountry() + "도시 : " + post.getCity() +  " 닉네임 : " + post.getNickname());
        }
        Assertions.assertEquals(posts.getSize(), 6);
    }

    @Test
    public void 게시글번호로_조회() {
        Long id = 31L;

        PostDetailResponseDto post = postService.findPostById(id);

        System.out.println("게시글 : " + id +"번 " + post.toString());
        Assertions.assertEquals(post.getTitle(), "동행구해용");
        Assertions.assertEquals(post.getContent(), "동행테스트입니다");

    }

    @Test
    public void 없는_게시글번호로_조회() {
        Long id = 100L;

        assertThrows(CustomException.class, () -> postService.findPostById(id));
    }

    @Test
    public void 조건별_게시글_조회() {
        Long postId = 2L;
        Pageable pageable = PageRequest.ofSize(6);
        PostSearchCondition condition = PostSearchCondition.builder()
                .parentCategoryId(1L)
                .recruitStatus(RecruitStatus.RECRUITING)
                .build();
        Slice<PostListResponseDto> posts = postService.getPostListByCondition(postId,pageable,condition);

        for(PostListResponseDto post : posts) {
            System.out.println("id : " + post.getId() + " title: " + post.getTitle() + " country : " + post.getCountry() +
                    "city : " + post.getCity() + " nickname : " + post.getNickname());
        }
        Assertions.assertEquals(posts.getSize(),6);

    }

    @Test
    public void 게시글_수정() {
        Long id = 31L;
        PostModifyRequestDto modifyRequestDto = PostModifyRequestDto.builder()
                .title("수정")
                .gender(Gender.MALE)
                .content("수정")
                .kakao_url("kakao_url")
                .recruitmentLimit(2)
                .recruitStatus(RecruitStatus.RECRUIT_COMPLETE)
                .travelStartDate("2024-02-28")
                .parentCategoryId(1L)
                .childCategoryId(8L)
                .build();

        Long updatedPostId = postService.modifyPost(id,modifyRequestDto);

        Assertions.assertEquals(updatedPostId,31L);

    }

    @Test
    public void 게시글_삭제(){
        Long id = 32L;

        postService.deletePost(id);

        Assertions.assertFalse(postService.isExistPost(id));
    }

}