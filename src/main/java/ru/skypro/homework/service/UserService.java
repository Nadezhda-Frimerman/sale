package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

public interface UserService {
    void setPassword (NewPasswordDto newPasswordDto);
    UserDto getCurrentUserInformation ();
    UpdateUserDto updateUserInformation (UpdateUserDto updateUserDto);
    User findUserById(Integer author);
}
