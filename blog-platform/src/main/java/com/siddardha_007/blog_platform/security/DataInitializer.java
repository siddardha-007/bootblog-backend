package com.siddardha_007.blog_platform.security;

import com.siddardha_007.blog_platform.model.Role;
import com.siddardha_007.blog_platform.model.User;
import com.siddardha_007.blog_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin(){
        return args -> {
            if(!userRepository.existsByEmail("admin0123456789@gmail.com")){
                User admin = new User();
                admin.setEmail("admin0123456789@gmail.com");
                admin.setUsername("ADMIN");
                admin.setPassword(
                        passwordEncoder.encode("admin9876543210")
                );
                admin.setRole(Role.ROLE_ADMIN);

                userRepository.save(admin);
            }
        };
    }
}
