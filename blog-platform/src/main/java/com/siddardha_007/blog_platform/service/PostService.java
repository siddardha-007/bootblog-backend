package com.siddardha_007.blog_platform.service;

import com.siddardha_007.blog_platform.dto.PostRequestDto;
import com.siddardha_007.blog_platform.dto.PostResponseDto;
import com.siddardha_007.blog_platform.dto.PostsDto;

public interface PostService {
    PostsDto getAllPosts();

    PostResponseDto getPostById(Long postId);

    PostsDto getPostsByCategory(Long categoryId);

    PostsDto getPostsByUser(Long userId);

    PostsDto getPostBySearch(String keyword);

    PostResponseDto createPost(PostRequestDto postRequestDto);

    PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto);

    String deletePost(Long postId);

}
