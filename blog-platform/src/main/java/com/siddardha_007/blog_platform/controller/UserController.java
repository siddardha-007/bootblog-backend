package com.siddardha_007.blog_platform.controller;

import com.siddardha_007.blog_platform.dto.UserResponseDto;
import com.siddardha_007.blog_platform.dto.UserRoleDto;
import com.siddardha_007.blog_platform.dto.UserUpdateDto;
import com.siddardha_007.blog_platform.repository.UserRepository;
import com.siddardha_007.blog_platform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId){
        UserResponseDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/me/role")
    public ResponseEntity<UserRoleDto> getUserRole(){
        UserRoleDto user = userService.getUserRole();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }



    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUserInfo(){
        UserResponseDto user = userService.getUserInfo();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        List<UserResponseDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateUsername(@Valid @RequestBody UserUpdateDto userUpdateDto){
        UserResponseDto user = userService.updateUsername(userUpdateDto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
}
