package com.siddardha_007.blog_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    private Long userId;
    private String username;

    private Long categoryId;
    private String categoryName;

    private List<CommentResponseDto> comments;
}
