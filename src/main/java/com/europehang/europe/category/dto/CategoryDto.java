package com.europehang.europe.category.dto;

import com.europehang.europe.category.domain.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    private String categoryName;
    private Long parent;
    private List<CategoryDto> children;

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .parent(category.getParent() != null ? category.getParent().getId() : 0)
                .children(category.getChildren().stream().map(CategoryDto::toDto).collect(Collectors.toList()))
                .build();
    }

}

