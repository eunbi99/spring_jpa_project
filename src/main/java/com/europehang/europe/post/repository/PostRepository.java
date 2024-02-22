package com.europehang.europe.post.repository;

import com.europehang.europe.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {

    Optional<Post> findById(Long id);

}
