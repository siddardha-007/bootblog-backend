package com.siddardha_007.blog_platform.service;

import com.siddardha_007.blog_platform.dto.UserResponseDto;
import com.siddardha_007.blog_platform.dto.UserRoleDto;
import com.siddardha_007.blog_platform.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    UserResponseDto getUserInfo();

    UserResponseDto updateUsername(UserUpdateDto userUpdateDto);

    List<UserResponseDto> getAllUsers();

    UserRoleDto getUserRole();

    UserResponseDto getUserById(Long userId);
}
