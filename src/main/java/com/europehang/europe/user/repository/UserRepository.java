package com.europehang.europe.user.repository;

import com.europehang.europe.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Optional<User> findAllByEmail(String email);
}
