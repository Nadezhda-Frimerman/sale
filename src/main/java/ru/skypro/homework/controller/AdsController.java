package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import javax.validation.Valid;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Tag(name = "Объявления", description = "Методы для работы с рекламными объявлениями")
public class AdsController {
    private AdsServiceImpl adsServiceImpl;

    @GetMapping
    @Operation(operationId = "getAllAds",
            summary = "Получение всех объявлений",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "200", description = "OK")
    public AdsDto getAllAds() {
        return new AdsDto();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(operationId = "addAd",
            summary = "Добавление объявления",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public AdDto addAd(@RequestPart("properties") @Valid CreateOrUpdateAdDto properties,
                       @RequestPart("image") MultipartFile image) {
        return new AdDto();
    }

    @GetMapping("/{id}")
    @Operation(operationId = "getAds",
            summary = "Получение информации об объявлении",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ExtendedAdDto getAds(@PathVariable(name = "id") Integer id) {
        return new ExtendedAdDto();
    }

    @DeleteMapping("/{id}")
    @Operation(operationId = "removeAd",
            summary = "Удаление объявления",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public void removeAd(@PathVariable(name = "id") Integer id) {
    }

    @PatchMapping("/{id}")
    @Operation(operationId = "updateAds",
            summary = "Обновление информации об объявлении",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public AdDto updateAds(@PathVariable(name = "id") Integer id,
                           @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto) {
        return new AdDto();
    }

    @GetMapping("/me")
    @Operation(operationId = "getAdsMe",
            summary = "Получение объявлений авторизованного пользователя",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public AdsDto getAdsMe() {
        return new AdsDto();
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(operationId = "updateImage",
            summary = "Обновление картинки объявления",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public byte[] updateImage(@PathVariable(name = "id") Integer id,
                            @RequestParam("image") MultipartFile image) {
        return new byte[0];
    }
}
