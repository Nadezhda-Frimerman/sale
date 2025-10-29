package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.impl.RegisterServiceImpl;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
//@RequiredArgsConstructor
public class RegisterController {
    private final RegisterServiceImpl registerService;

    public RegisterController(RegisterServiceImpl registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public void register(@RequestBody Register register) {
    }
}
