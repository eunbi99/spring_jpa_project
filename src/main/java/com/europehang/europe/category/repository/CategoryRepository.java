package com.europehang.europe.category.repository;

import com.europehang.europe.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>, CategoryRepositoryCustom {

    Boolean existsByCategoryName(String categoryName);

//    @Query(value = "SELECT c FROM Category c LEFT JOIN FETCH c.parent")
//    List<Category> findAllOrderByParentId1();

    List<Category> findByParentIsNull();
    Optional<Category> findById(Long id);

}
