package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import java.io.IOException;

@Service
public interface AdService {
    AdsDto getAllAds ();
    AdDto addAd (CreateOrUpdateAdDto properties);
    ExtendedAdDto getAdById (Integer id);
    void removeAd(Integer id);
    AdDto updateAd (Integer id, CreateOrUpdateAdDto createOrUpdateAdDto);
    AdsDto geyAllMyAds ();
    void uploadAdPicture(Integer id, MultipartFile file)throws IOException ;
}
