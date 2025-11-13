package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.impl.PictureServiceImpl;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@Tag(name = "Авторизация", description = "Методы для авторизации пользователя")
public class PictureController{
    private final PictureServiceImpl pictureServiceImpl;
    public PictureController(PictureServiceImpl pictureServiceImpl) {
        this.pictureServiceImpl = pictureServiceImpl;
    }

//    db
    @GetMapping("/pictures/{id}")
    public byte[] getPictures(PathVariable pathVariable) {
        log.error("ycvjbkjnklml");
        return new byte[0];
    }
}
