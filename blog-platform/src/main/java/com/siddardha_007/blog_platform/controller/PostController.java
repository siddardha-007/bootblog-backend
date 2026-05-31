package com.siddardha_007.blog_platform.controller;

import com.siddardha_007.blog_platform.dto.*;
import com.siddardha_007.blog_platform.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<PageResponseDto<PostResponseDto>> getAllPosts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ){
        PageResponseDto<PostResponseDto> posts = postService.getAllPosts(
                pageNumber,
                pageSize,
                sortBy,
                sortDir
        );
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId){
        PostResponseDto post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/posts")
    public ResponseEntity<PageResponseDto<PostResponseDto>> getPostsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ){
        PageResponseDto<PostResponseDto> posts =
                postService.getPostsByCategory(
                        categoryId,
                        pageNumber,
                        pageSize,
                        sortBy,
                        sortDir
                );

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<PostsDto> getPostsByUser(
            @PathVariable Long userId
    ){
        PostsDto posts =
                postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<PostsDto> getPostBySearch(
            @RequestParam String keyword
    ){
        PostsDto posts =
                postService.getPostBySearch(keyword);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto postRequestDto){
        PostResponseDto post = postService.createPost(postRequestDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId,@Valid @RequestBody PostRequestDto postRequestDto){
        PostResponseDto post = postService.updatePost(postId,postRequestDto);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        String deleted = postService.deletePost(postId);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }


}
