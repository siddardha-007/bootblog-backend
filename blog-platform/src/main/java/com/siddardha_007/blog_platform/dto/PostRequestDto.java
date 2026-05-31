package com.siddardha_007.blog_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;

    private String content;

    private String imageUrl;

    private Long categoryId;
}
