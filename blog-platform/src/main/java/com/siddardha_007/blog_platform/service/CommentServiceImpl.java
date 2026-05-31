package com.siddardha_007.blog_platform.service;

import com.siddardha_007.blog_platform.dto.CommentRequestDto;
import com.siddardha_007.blog_platform.dto.CommentResponseDto;
import com.siddardha_007.blog_platform.exceptions.BadRequestException;
import com.siddardha_007.blog_platform.exceptions.ResourceNotFoundException;
import com.siddardha_007.blog_platform.model.Comment;
import com.siddardha_007.blog_platform.model.Post;
import com.siddardha_007.blog_platform.model.Role;
import com.siddardha_007.blog_platform.model.User;
import com.siddardha_007.blog_platform.repository.CommentRepository;
import com.siddardha_007.blog_platform.repository.PostRepository;
import com.siddardha_007.blog_platform.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    public CommentRepository commentRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public CommentResponseDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment with id "+commentId+" is not found"));
        return mapToCommentResponseDto(comment);
    }

    @Override
    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        Post post = getPost(postId);

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream()
                .map(this::mapToCommentResponseDto)
                .toList();
    }

    @Override
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment with id "+commentId+" is not found"));
        User loggedInUser = getUser();

        if(!comment.getUser().getUserId().equals(loggedInUser.getUserId())){
            throw new BadRequestException("You can update only your own comments");
        }

        comment.setText(commentRequestDto.getText());

        Comment updatedComment = commentRepository.save(comment);

        return mapToCommentResponseDto(updatedComment);
    }

    @Override
    public String deleteComment(Long commentId) {

        System.out.println("DELETE COMMENT SERVICE CALLED");
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment with id "+commentId+" is not found"));
        User loggedInUser = getUser();

        System.out.println("Logged In User: " + loggedInUser.getEmail());
        System.out.println("Logged In Role: " + loggedInUser.getRole());

        System.out.println("Comment Owner: " + comment.getUser().getEmail());

        System.out.println(loggedInUser.getEmail());
        System.out.println(loggedInUser.getRole());
        if(!comment.getUser().getUserId().equals(loggedInUser.getUserId())
                && loggedInUser.getRole() != Role.ROLE_ADMIN){
            throw new BadRequestException("You can delete only your own Comments");
        }
        commentRepository.delete(comment);
        return "Comment is deleted successfully";
    }

    @Override
    public List<CommentResponseDto> getCommentsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with id"+userId+" is not found"));
        List<Comment> comments = commentRepository.findByUser(user);

        return comments.stream()
                .map(this::mapToCommentResponseDto)
                .toList();
    }

    @Override
    public List<CommentResponseDto> getCommentsByMe() {
        User user = getUser();
        List<Comment> comments = commentRepository.findByUser(user);

        return comments.stream()
                .map(this::mapToCommentResponseDto)
                .toList();
    }


    @Override
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment();
        comment.setText(commentRequestDto.getText());
        comment.setPost(getPost(postId));
        comment.setUser(getUser());

        Comment savedComment = commentRepository.save(comment);

        CommentResponseDto commentResponseDto = mapToCommentResponseDto(savedComment);

        return commentResponseDto;
    }




    //These are HELPER methods



    private CommentResponseDto mapToCommentResponseDto(Comment comment){
        CommentResponseDto dto = modelMapper.map(comment,CommentResponseDto.class);

        dto.setUserId(comment.getUser().getUserId());
        dto.setUsername(comment.getUser().getUsername());

        return dto;
    }



    private User getUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User not found with email: " + email));
        return user;
    }


    private Post getPost(Long postId){

        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post with id "+postId+" is not found"));

        return post;
    }


}
