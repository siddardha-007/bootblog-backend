package com.siddardha_007.blog_platform.dto;



import com.siddardha_007.blog_platform.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDto {
    private List<CategoryResponseDto> categories;
}
