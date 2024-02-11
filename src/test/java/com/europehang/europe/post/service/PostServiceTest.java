package com.europehang.europe.post.service;

import com.europehang.europe.category.repository.CategoryRepository;
import com.europehang.europe.common.enums.Gender;
import com.europehang.europe.common.enums.RecruitStatus;
import com.europehang.europe.exception.CustomException;
import com.europehang.europe.post.domain.Post;
import com.europehang.europe.post.dto.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PostServiceTest {

    @Autowired
    PostService postService;

    private static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void 게시글_등록() {
        PostRegisterRequestDto postRequest = PostRegisterRequestDto.builder()
                .title("테스트 테스트25")
                .gender(Gender.MALE)
                .content("테스트")
                .kakao_url("kakao_url")
                .recruitmentLimit(4)
                .travelStartDate("2024-02-28")
                .parentCategoryId(1L)
                .childCategoryId(8L)
                .build();

        Long id = postService.savePost(postRequest);

        Assertions.assertEquals(id,24);

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

        assertThrows(CustomException.class, () -> postService.savePost(postRequest));

    }

    @Test
    public void 모든_게시글_조회() {
        List<PostResponseDto> posts = postService.getAllPost();

        for(PostResponseDto post : posts) {
            System.out.println("글 번호 : " + post.getId()+ " 글 제목 : " + post.getTitle() + " 내용 : " + post.getContent());
        }
        Assertions.assertEquals(posts.size(), 23);
    }

    @Test
    public void 게시글번호로_조회() {
        Long id = 6L;

        PostDetailResponseDto post = postService.findPostById(id);

        Assertions.assertEquals(post.getTitle(), "파리 개선문 동행 구해요");
        Assertions.assertEquals(post.getContent(), "개선문 가고싶어요");

    }

    @Test
    public void 없는_게시글번호로_조회() {
        Long id = 100L;

        assertThrows(CustomException.class, () -> postService.findPostById(id));
    }

    @Test
    public void 조건별_게시글_조회() {
        PostSearchCondition condition = PostSearchCondition.builder()
                .parentCategoryId(2L)
                .isRecruitCompleted(RecruitStatus.RECRUIT_COMPLETE)
                .build();
        List<PostResponseDto> posts = postService.getPostListByCondition(condition);

        for(PostResponseDto post : posts) {
            System.out.println("id : " + post.getId() + " title: " + post.getTitle() + " country : " + post.getCountry() + "city : " + post.getCity() );
        }
        Assertions.assertEquals(posts.size(),1);

    }

    @Test
    public void 게시글_수정() {
        Long id = 22L;
        PostModifyRequestDto modifyRequestDto = PostModifyRequestDto.builder()
                .title("수정")
                .gender(Gender.MALE)
                .content("수정")
                .kakao_url("kakao_url")
                .recruitmentLimit(2)
                .recuritCompleteStatus(RecruitStatus.RECRUIT_COMPLETE)
                .travelStartDate("2024-02-28")
                .parentCategoryId(1L)
                .childCategoryId(8L)
                .build();

        Long updatedPostId = postService.modifyPost(id,modifyRequestDto);

        Assertions.assertEquals(updatedPostId,22L);

    }

    @Test
    public void 게시글_삭제(){
        Long id = 11L;

        postService.deletePost(id);


        Assertions.assertEquals(postService.getAllPost().size(),10);
    }
}