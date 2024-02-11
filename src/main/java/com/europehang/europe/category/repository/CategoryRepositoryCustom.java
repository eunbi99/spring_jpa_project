package com.europehang.europe.category.repository;

import com.europehang.europe.category.domain.Category;
import com.europehang.europe.category.dto.CategoryDto;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> findAllOrderByParentId();

    List<Category> findCategoryByParentId(Long parentId);
}
