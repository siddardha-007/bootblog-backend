package com.siddardha_007.blog_platform.service;

import com.siddardha_007.blog_platform.dto.PageResponseDto;
import com.siddardha_007.blog_platform.dto.PostRequestDto;
import com.siddardha_007.blog_platform.dto.PostResponseDto;
import com.siddardha_007.blog_platform.dto.PostsDto;

public interface PostService {
    PageResponseDto<PostResponseDto> getAllPosts(
            int pageNumber,
            int pageSize,
            String sortBy,
            String sortDir
    );

    PostResponseDto getPostById(Long postId);

    PageResponseDto<PostResponseDto> getPostsByCategory(
            Long categoryId,
            int pageNumber,
            int pageSize,
            String sortBy,
            String sortDir
    );

    PostsDto getPostsByUser(Long userId);

    PostsDto getPostBySearch(String keyword);

    PostResponseDto createPost(PostRequestDto postRequestDto);

    PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto);

    String deletePost(Long postId);

    PostsDto getPostsByMe();
}
