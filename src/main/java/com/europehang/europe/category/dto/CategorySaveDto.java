package com.europehang.europe.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategorySaveDto {
    private String name;
    private Long parentId;

    public CategorySaveDto(String name) {
        this.name = name;
    }
}
