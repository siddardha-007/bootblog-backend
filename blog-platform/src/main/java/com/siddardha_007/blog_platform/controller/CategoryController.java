package com.siddardha_007.blog_platform.controller;

import com.siddardha_007.blog_platform.dto.CategoriesDto;
import com.siddardha_007.blog_platform.dto.CategoryRequestDto;
import com.siddardha_007.blog_platform.dto.CategoryResponseDto;
import com.siddardha_007.blog_platform.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;
    @GetMapping("/categories")
    public ResponseEntity<CategoriesDto> getAllCategories(){
        CategoriesDto categoriesDto = categoryService.getAllCategories();
        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }


    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryRequestDto> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto){
        CategoryRequestDto savedCategoryRequestDto = categoryService.createCategory(categoryRequestDto);
        return new ResponseEntity<>(savedCategoryRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequestDto categoryRequestDto){
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryId,categoryRequestDto);
        return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDto> deleteCategory(@PathVariable Long categoryId){
        CategoryResponseDto categoryResponseDto = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "Authenticated";
    }


}
