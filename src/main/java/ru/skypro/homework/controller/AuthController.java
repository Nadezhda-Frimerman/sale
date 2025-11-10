package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.LoginDto;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import javax.validation.Valid;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@Tag(name = "Авторизация", description = "Методы для авторизации пользователя")
public class AuthController {
    private AuthServiceImpl authServiceImpl;

    public AuthController(AuthServiceImpl authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }

    @PostMapping("/login")
    @Operation(
            tags = {"Авторизация"},
            summary = "Авторизация пользователя",
            operationId = "login"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public void login(@RequestBody LoginDto loginDto) {
        authServiceImpl.login(loginDto);
    }

}
