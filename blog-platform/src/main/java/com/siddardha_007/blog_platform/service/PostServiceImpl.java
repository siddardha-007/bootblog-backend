package com.siddardha_007.blog_platform.service;

import com.siddardha_007.blog_platform.dto.*;
import com.siddardha_007.blog_platform.exceptions.BadRequestException;
import com.siddardha_007.blog_platform.exceptions.ResourceNotFoundException;
import com.siddardha_007.blog_platform.model.*;
import com.siddardha_007.blog_platform.repository.CategoryRepository;
import com.siddardha_007.blog_platform.repository.PostRepository;
import com.siddardha_007.blog_platform.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public ModelMapper modelMapper;


    @Override
    @Transactional(readOnly = true)
    public PageResponseDto getAllPosts(
            int pageNumber,
            int pageSize,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<PostResponseDto> postResponseDtos = posts.getContent().stream()
                .map(this::mapToPostResponseDto)
                .toList();

        PageResponseDto<PostResponseDto> pageResponseDto = new PageResponseDto<>();
        pageResponseDto.setPosts(postResponseDtos);
        pageResponseDto.setPageNumber(posts.getNumber());
        pageResponseDto.setPageSize(posts.getSize());
        pageResponseDto.setTotalElements(posts.getTotalElements());
        pageResponseDto.setTotalPages(posts.getTotalPages());
        pageResponseDto.setLast(posts.isLast());
        return pageResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post is not found"));
        PostResponseDto postResponseDto = mapToPostResponseDto(post);
        return postResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<PostResponseDto> getPostsByCategory(
            Long categoryId,
            int pageNumber,
            int pageSize,
            String sortBy,
            String sortDir
    ) {


        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category with id "+categoryId+" is not found"));

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Post> posts = postRepository.findByCategory(category,pageable);


        List<PostResponseDto> postDtos = posts.getContent()
                .stream()
                .map(this::mapToPostResponseDto)
                .toList();
        PageResponseDto<PostResponseDto> response = new PageResponseDto<>();

        response.setPosts(postDtos);
        response.setPageNumber(posts.getNumber());
        response.setPageSize(posts.getSize());
        response.setTotalElements(posts.getTotalElements());
        response.setTotalPages(posts.getTotalPages());
        response.setLast(posts.isLast());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PostsDto getPostsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with id "+userId+" is not found"));
        List<Post> posts = postRepository.findByUser(user);

        List<PostResponseDto> postResponseDtos = posts.stream()
                .map(this::mapToPostResponseDto)
                .toList();
        PostsDto postsDto = new PostsDto();
        postsDto.setPosts(postResponseDtos);
        return postsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PostsDto getPostsByMe() {
        User user = getUser();
        List<Post> posts = postRepository.findByUser(user);

        List<PostResponseDto> postResponseDtos = posts.stream()
                .map(this::mapToPostResponseDto)
                .toList();
        PostsDto postsDto = new PostsDto();
        postsDto.setPosts(postResponseDtos);
        return postsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PostsDto getPostBySearch(String keyword) {
        List<Post> posts = postRepository.findByTitleContainingIgnoreCase(keyword);

        if(posts.isEmpty()){
            throw new ResourceNotFoundException("No posts are found with keyword: "+keyword);
        }

        List<PostResponseDto> postResponseDtos= posts.stream()
                .map(this::mapToPostResponseDto)
                .toList();
        PostsDto postsDto = new PostsDto();
        postsDto.setPosts(postResponseDtos);
        return postsDto;
    }

    @Override
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setImageUrl(postRequestDto.getImageUrl());
        post.setUser(getUser());
        post.setCategory(getCategory(postRequestDto.getCategoryId()));
        post.setCreatedAt(LocalDateTime.now());


        Post savedPost = postRepository.save(post);

        PostResponseDto postResponseDto = mapToPostResponseDto(savedPost);

        return postResponseDto;
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto) {
        User loggedInUser = getUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post with id "+postId+" is not found"));

        if(!post.getUser().getUserId().equals(loggedInUser.getUserId()) &&
                loggedInUser.getRole() != Role.ROLE_ADMIN){
            throw new BadRequestException("You can update only your own posts");
        }
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setImageUrl(postRequestDto.getImageUrl());

        post.setCategory(
                getCategory(postRequestDto.getCategoryId())
        );

        Post updatedPost = postRepository.save(post);

        return mapToPostResponseDto(updatedPost);
    }

    @Override
    @Transactional
    public String deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post with id "+postId+" is not found"));
        User loggedInUser = getUser();

        if(!post.getUser().getUserId().equals(loggedInUser.getUserId()) &&
                loggedInUser.getRole() != Role.ROLE_ADMIN){
            throw new BadRequestException("You can delete only your own posts");
        }

        postRepository.delete(post);
        return "Post is Deleted Successfully";
    }




    //These are HELPER methods !!!!





    private List<CommentResponseDto> mapComments(List<Comment> comments){
        return comments.stream()
                .map(comment -> modelMapper.map(comment,CommentResponseDto.class))
                .toList();
    }



    private PostResponseDto mapToPostResponseDto(Post post){
        PostResponseDto postResponseDto = modelMapper.map(post, PostResponseDto.class);

        postResponseDto.setUserId(post.getUser().getUserId());
        postResponseDto.setUsername(post.getUser().getUsername());

        postResponseDto.setCategoryId(post.getCategory().getCategoryId());
        postResponseDto.setCategoryName(post.getCategory().getCategoryName());

        List<CommentResponseDto> comments = post.getComments() == null ? List.of() : post.getComments().stream()
                .map(comment -> {
                    CommentResponseDto commentDto = modelMapper.map(comment,CommentResponseDto.class);
                    commentDto.setUserId(comment.getUser().getUserId());
                    commentDto.setUsername(comment.getUser().getUsername());
                    return commentDto;
                })
                .toList();
        postResponseDto.setComments(comments);
        return postResponseDto;
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

    private Category getCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category with id "+categoryId+" is not found"));
        return category;
    }
}
