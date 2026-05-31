package com.siddardha_007.blog_platform.controller;

import com.siddardha_007.blog_platform.dto.AuthResponseDto;
import com.siddardha_007.blog_platform.dto.LoginRequestDto;
import com.siddardha_007.blog_platform.dto.RegisterRequestDto;
import com.siddardha_007.blog_platform.security.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDto registerRequestDto){
        return new ResponseEntity<>(authenticationService.register(registerRequestDto), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return new ResponseEntity<>(authenticationService.login(loginRequestDto),HttpStatus.OK);
    }
}
