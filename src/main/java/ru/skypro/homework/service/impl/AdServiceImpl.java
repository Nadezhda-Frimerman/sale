package ru.skypro.homework.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Picture;
import ru.skypro.homework.entity.PictureOwner;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.MyUserDetailsManager;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {
    private AdRepository adRepository;
    private AdMapper adMapper;
    private MyUserDetailsManager myUserDetailsManager;
    private PasswordEncoder encoder;
    private final PictureServiceImpl pictureServiceImpl;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper, MyUserDetailsManager myUserDetailsManager, PasswordEncoder encoder, PictureServiceImpl pictureServiceImpl) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
        this.myUserDetailsManager = myUserDetailsManager;
        this.encoder = encoder;
        this.pictureServiceImpl = pictureServiceImpl;
    }

    //    Получение всех объявлений
    @Override
    public AdsDto getAllAds() {
        List<Ad> ads = adRepository.findAll();
        List<AdDto> allAds = adMapper.AdListToAdDtoList(ads);
        AdsDto adsDto = new AdsDto();
        adsDto.setResults(allAds);
        adsDto.setCount(allAds.size());
        return adsDto;
    }

    //    Добавление объявления
    @Override
    public AdDto addAd(CreateOrUpdateAdDto properties) {
        myUserDetailsManager.checkUserAuthenticated();
        Ad ad = adMapper.CreateOrUpdateAdDtoToAdEntity(properties);
        adRepository.save(ad);
        return adMapper.AdToAdDto(ad);
    }

    //Получение информации об объявлении по id объявления
    @Override
    public ExtendedAdDto getAdById(Integer id) {
        Optional<Ad> ad = adRepository.findById(id);
        return adMapper.AdtoExtendedAdDto(ad.orElseThrow());
    }

    //    Удаление объявления
    @Override
    public void removeAd(Integer id) {
        myUserDetailsManager.checkUserAuthenticated();
        if (!adRepository.getById(id).getUser().equals(myUserDetailsManager.getCurrentUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        adRepository.deleteById(id);
    }

    //Обновление информации об объявлении
    @Override
    public AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        myUserDetailsManager.checkUserAuthenticated();
        if (!adRepository.getById(id).getUser().equals(myUserDetailsManager.getCurrentUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Ad ad = findAdById(id);
        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        ad.setDescription(createOrUpdateAdDto.getDescription());
        adRepository.save(ad);
        AdDto adDto = adMapper.AdToAdDto(ad);
        return adDto;
    }

    //Получение объявлений авторизованного пользователя
    @Override
    public AdsDto geyAllMyAds() {
        myUserDetailsManager.checkUserAuthenticated();
        List<AdDto> adsDtos = adMapper.AdListToAdDtoList(adRepository.findAllMyAds(myUserDetailsManager.getCurrentUser().getId()));
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(adsDtos.size());
        adsDto.setResults(adsDtos);
        return adsDto;
    }

    //    Обновление картинки объявления
    @Override
    public byte[] uploadAdPicture(Integer id, MultipartFile image) throws IOException {
        Picture picture = pictureServiceImpl.uploadPicture(id, PictureOwner.AD, image);
        Ad currentAd = findAdById(id);
        currentAd.setImage(picture);
        adRepository.save(currentAd);
        return findAdById(id).getImage().getData();
    }

    public Ad findAdById(Integer id) {
        return adRepository.findById(id).orElseThrow();
    }
}
