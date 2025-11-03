package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
    private final UserRepository userRepository;

    public UsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Integer author) {
        return userRepository.findById(author).orElseThrow();
    }
}
