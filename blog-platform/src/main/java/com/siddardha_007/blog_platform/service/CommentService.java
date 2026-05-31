package com.siddardha_007.blog_platform.service;

import com.siddardha_007.blog_platform.dto.CommentRequestDto;
import com.siddardha_007.blog_platform.dto.CommentResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(Long postId,@Valid CommentRequestDto commentRequestDto);

    CommentResponseDto getCommentById(Long commentId);

    List<CommentResponseDto> getCommentsByPost(Long postId);

    CommentResponseDto updateComment(Long commentId, @Valid CommentRequestDto commentRequestDto);

    String deleteComment(Long commentId);

    List<CommentResponseDto> getCommentsByUser(Long userId);

    List<CommentResponseDto> getCommentsByMe();
}
