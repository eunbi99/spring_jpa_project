package com.europehang.europe.post.service;

import com.europehang.europe.post.domain.Post;
import com.europehang.europe.post.domain.PostLike;
import com.europehang.europe.post.repository.PostLikeRepository;
import com.europehang.europe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;

    @Transactional
    public void createOrDeleteLike(User user, Long postId) {
        Optional<PostLike> likedPost = postLikeRepository.findLikeByUserIdAndPostId(user.getId(),postId);

        if(likedPost.isPresent()) {
            deletePostLike(likedPost.get().getId());
        } else {
            createPostLike(user,postId);
        }

    }

    public void createPostLike(User user, Long postId) {
        Post post = postService.findPost(postId);

        PostLike postLike = PostLike.createPostLike(user,post);

        postLikeRepository.save(postLike);
    }


    public void deletePostLike(Long postLikeId) {
        postLikeRepository.deleteById(postLikeId);
    }

}
