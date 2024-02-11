package com.europehang.europe.category.controller;

import com.europehang.europe.category.domain.Category;
import com.europehang.europe.category.dto.CategoryDto;
import com.europehang.europe.category.dto.CategorySaveDto;
import com.europehang.europe.category.service.CategoryService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CategoryControllerTest {
    @Autowired
    CategoryService categoryService;
    @Autowired
    EntityManager em;

}