package com.siddardha_007.blog_platform.service;

import com.siddardha_007.blog_platform.dto.CategoriesDto;
import com.siddardha_007.blog_platform.dto.CategoryRequestDto;
import com.siddardha_007.blog_platform.dto.CategoryResponseDto;
import com.siddardha_007.blog_platform.exceptions.BadRequestException;
import com.siddardha_007.blog_platform.exceptions.ResourceNotFoundException;
import com.siddardha_007.blog_platform.model.Category;
import com.siddardha_007.blog_platform.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoryService{

    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public CategoriesDto getAllCategories() {
        List<Category> categories =categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new BadRequestException("Categories are empty !!!");
        }

        List<CategoryResponseDto> categoryResponseDtos = categories.stream()
                .map(category -> modelMapper.map(category,CategoryResponseDto.class))
                .toList();

        CategoriesDto categoriesDto = new CategoriesDto();
        categoriesDto.setCategories(categoryResponseDtos);
        return categoriesDto;
    }

    @Override
    public CategoryRequestDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = modelMapper.map(categoryRequestDto, Category.class);
        Category categoryFromDb =categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFromDb != null){
            throw new BadRequestException("Category with name "+category.getCategoryName()+" already existed !!!");
        }
        Category categorySaved =categoryRepository.save(category);
        return modelMapper.map(categorySaved, CategoryRequestDto.class);
    }

    @Override
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category with id "+categoryId+" is not found"));
        savedCategory.setCategoryName(categoryRequestDto.getCategoryName());

        Category updatedCategory = categoryRepository.save(savedCategory);

        return modelMapper.map(updatedCategory,CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto deleteCategory(Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category with id "+categoryId+" is not found"));

        if(!savedCategory.getPosts().isEmpty()){
            throw new BadRequestException(
                    "Cannot delete category because it contains posts"
            );
        }

        categoryRepository.delete(savedCategory);
        return modelMapper.map(savedCategory,CategoryResponseDto.class);
    }
}
