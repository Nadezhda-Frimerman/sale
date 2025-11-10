package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {
    private AdRepository adRepository;
    private AdMapper adMapper;

    public AdServiceImpl(AdRepository adRepository,AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }
    @Override
    public AdsDto getAllAds (){
        List<Ad> ads = adRepository.findAll();
        List <AdDto> allAds = adMapper.AdListToAdDtoList(ads);
        AdsDto adsDto = new AdsDto();
        adsDto.setResults(allAds);
        adsDto.setCount(allAds.size());
        return adsDto;
    }
    @Override
    public ExtendedAdDto getAdById (Integer id){
        Optional<Ad> ad = adRepository.findById(id);

        return adMapper.AdtoExtendedAdDto(ad.orElseThrow());
    }

}
