package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.MyUserDetailsManager;
import ru.skypro.homework.service.RegisterService;

import java.util.Base64;

@Service
public class RegisterServiceImpl implements RegisterService {
    private MyUserDetailsManager myUserDetailsManager;
    private PasswordEncoder passwordEncoder;
    public void registerUser (RegisterDto registerDto){
        // Encode password using PasswordEncoder
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        // Create UserDetails object
        UserDetails userDetails = (UserDetails) User.builder()
                .email(registerDto.getUsername())
                .password(encodedPassword)
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .phone(registerDto.getPhone())
                .role(registerDto.getRole())
                .build();

        // Register user via UserDetailsManager
        myUserDetailsManager.createUser(userDetails);

    }
}
