package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {
    private AdsServiceImpl adsServiceImpl;

    @GetMapping
    @Operation(operationId = "getAllAds", summary = "Получение всех объявлений", tags = {"Объявления"})
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<Ad>> getAllAds() {
//    adsServiceImpl.getAllAds;
        return ResponseEntity.ok(List.of());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Ad addAd(@RequestPart("properties") @Valid CreateOrUpdateAd properties,
                    @RequestPart("image") MultipartFile image) {
        return new Ad();
    }

    @GetMapping("/{id}")
    public ExtendedAd getAds(@PathVariable(name = "id") Integer id) {
        return new ExtendedAd();
    }

    @DeleteMapping("/{id}")
    public void removeAd(@PathVariable(name = "id") Integer id) {
    }

    @PatchMapping("/{id}")
    public Ad updateAds(@PathVariable(name = "id") Integer id,
                          @RequestBody CreateOrUpdateAd createOrUpdateAd) {
        return new Ad();
    }

    @GetMapping("/me")
    public Ads getAdsMe() {
        return new Ads();
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] updateImage(@PathVariable(name = "id") Integer id,
                            @RequestParam("image") MultipartFile image) {
        return new byte[0];
    }
}
