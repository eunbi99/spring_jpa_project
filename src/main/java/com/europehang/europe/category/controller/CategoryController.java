package com.europehang.europe.category.controller;

import com.europehang.europe.category.dto.CategoryDto;
import com.europehang.europe.category.dto.CategorySaveDto;
import com.europehang.europe.category.service.CategoryService;
import com.europehang.europe.common.dto.ApiResponseDto;
import com.europehang.europe.common.enums.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<ApiResponseDto> getCategoryList() {
        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage(),categoryService.getCategory());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponseDto> getCategoryListByParentId(@PathVariable("categoryId") Long categoryId) {
        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage(),categoryService.getCategoryByParentId(categoryId));

        return ResponseEntity.ok(res);
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiResponseDto> saveCategory(@RequestBody CategorySaveDto categorySaveDto) {
        categoryService.saveCategory(categorySaveDto);

        ApiResponseDto res = ApiResponseDto.of(ResponseStatus.OK, ResponseStatus.OK.getMessage());
        return ResponseEntity.ok(res);
    }
}
