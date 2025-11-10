package ru.skypro.homework.service;


import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.LoginDto;
@Service
public interface AuthService {
    void login(LoginDto loginDto);


}
