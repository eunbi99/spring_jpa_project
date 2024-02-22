package com.europehang.europe.post.repository;

import com.europehang.europe.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {

    Optional<PostLike> findLikeByUserIdAndPostId(Long userId, Long postId);
    void deleteById(Long id);
}
