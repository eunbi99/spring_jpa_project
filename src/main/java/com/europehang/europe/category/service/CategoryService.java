package com.europehang.europe.category.service;

import com.europehang.europe.category.domain.Category;
import com.europehang.europe.category.dto.CategoryDto;
import com.europehang.europe.category.dto.CategorySaveDto;
import com.europehang.europe.category.repository.CategoryRepository;
import com.europehang.europe.exception.CustomException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.europehang.europe.common.enums.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDto> getCategory() {
        return categoryRepository.findAllOrderByParentId().stream()
                .map(CategoryDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoryByParentId(Long categoryId) {
        return categoryRepository.findCategoryByParentId(categoryId).stream()
                .map(CategoryDto::toDto)
                .collect(Collectors.toList());
    }

    public void saveCategory(CategorySaveDto saveDto) {
        if(isExistCategory(saveDto.getName())) {
            throw new CustomException(CATEGORY_ALREADY_EXIST);
        }

        Category parentId = Optional.ofNullable(saveDto.getParentId())
                .map(id -> categoryRepository.findById(Long.valueOf(id))
                        .orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND)))
                .orElse(null);

        categoryRepository.save(Category.builder()
                .categoryName(saveDto.getName())
                .parent(parentId)
                .build());
    }

    // 이미 등록된 카테고리인지 체크
    public boolean isExistCategory(String name) {
        return categoryRepository.existsByCategoryName(name);
    }

}
