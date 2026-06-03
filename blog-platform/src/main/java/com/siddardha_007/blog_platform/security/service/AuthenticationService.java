package com.siddardha_007.blog_platform.security.service;

import com.siddardha_007.blog_platform.dto.AuthResponseDto;
import com.siddardha_007.blog_platform.dto.LoginRequestDto;
import com.siddardha_007.blog_platform.dto.RegisterRequestDto;
import com.siddardha_007.blog_platform.exceptions.BadRequestException;
import com.siddardha_007.blog_platform.model.Role;
import com.siddardha_007.blog_platform.model.User;
import com.siddardha_007.blog_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.LobMergeStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequestDto request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email already exists with "+request.getEmail());
        }
        User user = new User();

        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.ROLE_USER);

        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponseDto login(LoginRequestDto request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        System.out.println(authentication.isAuthenticated());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        Long userId = userDetails.getUserId();
        String email = userDetails.getUsername();

        return new AuthResponseDto(token, userId, email);
    }

}
