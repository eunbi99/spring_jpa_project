package com.europehang.europe.category.controller;

import com.europehang.europe.category.dto.CategorySaveDto;
import com.europehang.europe.category.service.CategoryService;
import com.europehang.europe.common.dto.ApiResponse;
import com.europehang.europe.common.dto.ErrorResponse;
import com.europehang.europe.common.enums.ResponseStatus;
import com.europehang.europe.common.handler.CustomExceptionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 전체 조회", description = "등록된 카테고리를 모두 조회 한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse> getCategoryList() {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, categoryService.getCategory());
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "카테고리 ID로 조회", description = "카테고리ID로 해당 카테고리의 하위 카테고리까지 조회 한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryListByParentId(@PathVariable("categoryId") Long categoryId) {
        ApiResponse res = ApiResponse.of(ResponseStatus.OK, categoryService.getCategoryByParentId(categoryId));

        return ResponseEntity.ok(res);
    }

    @Operation(summary = "카테고리 등록", description = "카테고리를 등록 한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "카테고리 등록 성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = CustomExceptionHandler.class)))})
    @PostMapping("/categories")
    public ResponseEntity<ApiResponse> saveCategory(@RequestBody CategorySaveDto categorySaveDto) {
        categoryService.saveCategory(categorySaveDto);

        ApiResponse res = ApiResponse.of(ResponseStatus.OK);
        return ResponseEntity.ok(res);
    }
}
