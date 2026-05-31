package com.siddardha_007.blog_platform.service;

import com.siddardha_007.blog_platform.dto.UserResponseDto;
import com.siddardha_007.blog_platform.dto.UserUpdateDto;
import com.siddardha_007.blog_platform.exceptions.ResourceNotFoundException;
import com.siddardha_007.blog_platform.model.User;
import com.siddardha_007.blog_platform.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
