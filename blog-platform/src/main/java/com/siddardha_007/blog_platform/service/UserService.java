package com.siddardha_007.blog_platform.service;

import com.siddardha_007.blog_platform.dto.UserResponseDto;
import com.siddardha_007.blog_platform.dto.UserUpdateDto;

public interface UserService {
    UserResponseDto getUserInfo();

    UserResponseDto updateUsername(UserUpdateDto userUpdateDto);
}
