package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;

@Service
public interface AdService {
    AdsDto getAllAds ();
    AdDto addAd (CreateOrUpdateAdDto properties);
    ExtendedAdDto getAdById (Integer id);
    void removeAd(Integer id);
}
