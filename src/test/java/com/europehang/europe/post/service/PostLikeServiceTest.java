package com.europehang.europe.post.service;


import com.europehang.europe.post.repository.PostLikeRepository;
import com.europehang.europe.user.domain.User;
import com.europehang.europe.user.repository.UserRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;


@SpringBootTest
@Transactional
@Rollback(value = false)
class PostLikeServiceTest {

    @Autowired
    PostLikeService postLikeService;

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
    public void 좋아요() {
        Long id = 7L;
        Long postId = 3L;
        Optional<User> user = userRepository.findById(id);

        postLikeService.createOrDeleteLike(user.get(),postId);

        Assertions.assertEquals(postLikeRepository.findAll().size(),2);
    }
}