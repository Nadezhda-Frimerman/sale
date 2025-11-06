package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import javax.persistence.EntityNotFoundException;

@Service
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;

    public AdServiceImpl(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public Ad getAdById(Integer adId) {
        return adRepository.findById(adId)
                .orElseThrow(() -> new EntityNotFoundException("Ad not found"));
    }
}
