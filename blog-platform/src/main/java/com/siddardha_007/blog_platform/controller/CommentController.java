package com.siddardha_007.blog_platform.controller;

import com.siddardha_007.blog_platform.dto.CommentRequestDto;
import com.siddardha_007.blog_platform.dto.CommentResponseDto;
import com.siddardha_007.blog_platform.dto.PostRequestDto;
import com.siddardha_007.blog_platform.dto.PostResponseDto;
import com.siddardha_007.blog_platform.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(@PathVariable Long postId){
        List<CommentResponseDto> comments = commentService.getCommentsByPost(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long commentId){
        CommentResponseDto comment = commentService.getCommentById(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }


    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByUser(@PathVariable Long userId){
        List<CommentResponseDto> comments = commentService.getCommentsByUser(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }



    @GetMapping("/users/me/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByMe(){
        List<CommentResponseDto> comments = commentService.getCommentsByMe();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }



    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId, @Valid @RequestBody CommentRequestDto commentRequestDto){
        CommentResponseDto comment = commentService.createComment(postId, commentRequestDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }


    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentRequestDto commentRequestDto){
        CommentResponseDto comment = commentService.updateComment(commentId, commentRequestDto);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){

        String deleted = commentService.deleteComment(commentId);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

}
