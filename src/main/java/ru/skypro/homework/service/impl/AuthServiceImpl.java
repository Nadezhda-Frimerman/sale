package ru.skypro.homework.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.LoginDto;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.MyUserDetailsManager;

@Service
public class AuthServiceImpl implements AuthService {

    private final MyUserDetailsManager myUserDetailsManager;
    private final PasswordEncoder encoder;
    private UserRepository userRepository;
    private UserMapper userMapper;

    public AuthServiceImpl(MyUserDetailsManager myUserDetailsManager,
                           PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper userMapper) {
        this.myUserDetailsManager = myUserDetailsManager;
        this.encoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void login(LoginDto loginDto) {
        if (myUserDetailsManager.userExists(loginDto.getUsername())) {
            UserDetails userDetails = myUserDetailsManager.loadUserByUsername(loginDto.getUsername());
            encoder.matches(loginDto.getPassword(),userDetails.getPassword());
        }
        else {throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }
   public boolean register(RegisterDto registerDto){
       if (myUserDetailsManager.userExists(registerDto.getUsername())) {
           return false;
       }
       User user = userMapper.RegisterDtoToUserEntity(registerDto);
       userRepository.save(user);
       return true;
    }

}
