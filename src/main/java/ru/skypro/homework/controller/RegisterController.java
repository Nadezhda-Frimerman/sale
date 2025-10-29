package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.impl.RegisterServiceImpl;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@Tag(name = "Регистрация", description = "Методы для регистрации пользователя")
@RequiredArgsConstructor
public class RegisterController {
    private RegisterServiceImpl registerServiceImpl;

    @PostMapping("/register")
    @Operation(
            tags = {"Регистрация"},
            summary = "Регистрация пользователя",
            operationId = "register"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<String> register(@RequestBody Register register) {

        if (true) {
            return ResponseEntity.ok("Регистрация успешна");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверные данные");
        }

    }

}
