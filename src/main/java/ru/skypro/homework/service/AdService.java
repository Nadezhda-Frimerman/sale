package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.ExtendedAdDto;

@Service
public interface AdService {
    AdsDto getAllAds ();
    ExtendedAdDto getAdById (Integer id);
}
