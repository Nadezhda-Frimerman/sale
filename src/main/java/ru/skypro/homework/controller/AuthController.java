package ru.skypro.homework.controller;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.AuthService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {
//    @EqualsAndHashCode.Exclude
    private final AuthService authService;
//    make all controllers alike

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        if (authService.login(login.getUsername(), login.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody Register register) {
//        if (authService.register(register)) {
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
}
