package com.siddardha_007.blog_platform.service;

import com.siddardha_007.blog_platform.dto.CategoriesDto;
import com.siddardha_007.blog_platform.dto.CategoryRequestDto;
import com.siddardha_007.blog_platform.dto.CategoryResponseDto;

public interface CategoryService {

    CategoriesDto getAllCategories();

    CategoryRequestDto createCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto);

    CategoryResponseDto deleteCategory(Long categoryId);



}
