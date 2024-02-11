package com.europehang.europe.category.repository;

import com.europehang.europe.category.domain.Category;
import com.europehang.europe.category.domain.QCategory;
import com.europehang.europe.category.dto.CategoryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.europehang.europe.category.domain.QCategory.category;

@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

   // @Query(value = "SELECT c FROM Category c LEFT JOIN FETCH c.parent p ORDER BY p.id ASC NULLS FIRST, c.id ASC")
//    List<Category> findAllOrderByParentId();
    @Override
    public List<Category> findAllOrderByParentId() {
        QCategory parent = new QCategory("parent");
        QCategory child = new QCategory("child");
        return queryFactory
                .selectFrom(parent)
                .distinct()
                .leftJoin(parent.children, child).fetchJoin()
                .where(parent.parent.isNull())
                .orderBy(parent.id.asc(),child.id.asc())
                .fetch();
    }

    @Override
    public List<Category> findCategoryByParentId(Long categoryId) {
        QCategory parent = new QCategory("parent");
        QCategory child = new QCategory("child");
        return queryFactory
                .select(parent)
                .from(parent)
                .leftJoin(parent.children, child).fetchJoin()
                .where(parent.id.eq(categoryId))
                .orderBy(parent.id.asc(),child.id.asc())
                .fetch();
    }
}
