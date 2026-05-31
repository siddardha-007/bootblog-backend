package com.siddardha_007.blog_platform.controller;

import com.siddardha_007.blog_platform.dto.UserResponseDto;
import com.siddardha_007.blog_platform.dto.UserUpdateDto;
import com.siddardha_007.blog_platform.repository.UserRepository;
import com.siddardha_007.blog_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUserInfo(){
        UserResponseDto user = userService.getUserInfo();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateUsername(@RequestBody UserUpdateDto userUpdateDto){
        UserResponseDto user = userService.updateUsername(userUpdateDto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
}
