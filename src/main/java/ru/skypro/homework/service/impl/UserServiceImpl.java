package ru.skypro.homework.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.MyUserDetailsManager;
import ru.skypro.homework.service.UserService;

import java.beans.Encoder;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private UserMapper userMapper;
    private MyUserDetailsManager myUserDetailsManager;
    private PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, MyUserDetailsManager myUserDetailsManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.myUserDetailsManager = myUserDetailsManager;
    }
    @Override
    public void setPassword (NewPasswordDto newPasswordDto){
        checkUserAuthenticated();
        if (!encoder.matches(myUserDetailsManager.getCurrentUser().getPassword(), newPasswordDto.getCurrentPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (newPasswordDto.getNewPassword().length()<8 || newPasswordDto.getNewPassword().length()>16){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        String encodedNewPassword = encoder.encode(newPasswordDto.getNewPassword());
        User user = myUserDetailsManager.getCurrentUser();
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
    }
    @Override
    public UserDto getCurrentUserInformation (){
        checkUserAuthenticated();
        return userMapper.UserToUserDto(myUserDetailsManager.getCurrentUser());
    }
    @Override
    public UpdateUserDto updateUserInformation (UpdateUserDto updateUserDto){
        checkUserAuthenticated();
        User user = myUserDetailsManager.getCurrentUser();
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        userRepository.save(user);
        return userMapper.UserToUpdateUserDto(user);
    }
    @Override
    public User findUserById(Integer author) {
        return userRepository.findById(author).orElseThrow();
    }

    public static void checkUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
