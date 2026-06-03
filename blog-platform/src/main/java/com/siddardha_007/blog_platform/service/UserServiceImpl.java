package com.siddardha_007.blog_platform.service;

import com.siddardha_007.blog_platform.dto.UserResponseDto;
import com.siddardha_007.blog_platform.dto.UserRoleDto;
import com.siddardha_007.blog_platform.dto.UserUpdateDto;
import com.siddardha_007.blog_platform.exceptions.ResourceNotFoundException;
import com.siddardha_007.blog_platform.model.User;
import com.siddardha_007.blog_platform.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponseDto getUserInfo() {

        User loggedInUser = getUser();

        UserResponseDto userResponseDto = modelMapper.map(loggedInUser,UserResponseDto.class);
        return userResponseDto;
    }

    @Override
    public UserResponseDto updateUsername(UserUpdateDto userUpdateDto) {
        User loggedInUser = getUser();
        loggedInUser.setUsername(userUpdateDto.getUsername());
        User updatedUser = userRepository.save(loggedInUser);
        UserResponseDto userResponseDto = modelMapper.map(updatedUser,UserResponseDto.class);
        return userResponseDto;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = users.stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .toList();
        return userResponseDtos;
    }

    @Override
    public UserRoleDto getUserRole() {
        User user = getUser();
        UserRoleDto userRoleDto = modelMapper.map(user,UserRoleDto.class);
        return userRoleDto;
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User is not found with id "+userId));
        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
        return userResponseDto;
    }

    //These are HELPER methods

    private User getUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User with not found with email "+email));
        return user;
    }


}
