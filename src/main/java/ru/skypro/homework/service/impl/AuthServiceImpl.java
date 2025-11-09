package ru.skypro.homework.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.LoginDto;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.MyUserDetailsManager;

@Service
public class AuthServiceImpl implements AuthService {

    private final MyUserDetailsManager myUserDetailsManager;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(MyUserDetailsManager myUserDetailsManager,
                           PasswordEncoder passwordEncoder) {
        this.myUserDetailsManager = myUserDetailsManager;
        this.encoder = passwordEncoder;
    }

    @Override
    public void login(LoginDto loginDto) {
        if (myUserDetailsManager.userExists(loginDto.getUsername())) {
            UserDetails userDetails = myUserDetailsManager.loadUserByUsername(loginDto.getUsername());
            encoder.matches(loginDto.getPassword(), userDetails.getPassword());
        }
        else {throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }

}
