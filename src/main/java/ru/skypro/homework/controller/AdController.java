package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.impl.AdServiceImpl;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "Методы для работы с рекламными объявлениями")
public class AdController {
    private AdServiceImpl adServiceImpl;

    public AdController(AdServiceImpl adServiceImpl) {
        this.adServiceImpl = adServiceImpl;
    }

    @GetMapping
    @Operation(operationId = "getAllAds",
            summary = "Получение всех объявлений",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "200", description = "OK")
    public AdsDto getAllAds() {
        return new AdsDto();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "addAd",
            summary = "Добавление объявления",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public AdDto addAd(@RequestPart("properties") @Valid CreateOrUpdateAdDto properties,
                       @RequestPart("image") MultipartFile image) {
        return adServiceImpl.addAd(properties);
    }

    @GetMapping("/{id}")
    @Operation(operationId = "getAds",
            summary = "Получение информации об объявлении",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ExtendedAdDto getAds(@PathVariable(name = "id") Integer id) {
        return adServiceImpl.getAdById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @adService.getAdById(#id).author.email == authentication.name")
    @Operation(operationId = "removeAd",
            summary = "Удаление объявления",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public void removeAd(@PathVariable(name = "id") Integer id) {
        adServiceImpl.removeAd(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@adService.getAdById(#id).author.email == authentication.name")
    @Operation(operationId = "updateAds",
            summary = "Обновление информации об объявлении",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public AdDto updateAds(@PathVariable(name = "id") Integer id,
                           @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto) {
        return adServiceImpl.updateAd(id, createOrUpdateAdDto);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "getAdsMe",
            summary = "Получение объявлений авторизованного пользователя",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public AdsDto getAdsMe() {
        return adServiceImpl.geyAllMyAds();
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@adService.getAdById(#id).author.email == authentication.name")
    @Operation(operationId = "updateImage",
            summary = "Обновление картинки объявления",
            tags = {"Объявления"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public byte[] updateImage(@PathVariable(name = "id") Integer id,
                              @RequestParam("image") MultipartFile image) throws IOException {
        return adServiceImpl.uploadAdPicture(id, image);
    }
}
