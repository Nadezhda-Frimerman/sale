package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdMapper {
    /**
     * Ad <---> AdDto
     */

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "image", source = "image.filePath")
    AdDto AdToAdDto(Ad ad);

    /**
     * Ad <---> ExtendedAdDto
     */
    @Mapping(source = "id", target = "pk")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(target = "image", ignore = true)
//    @Mapping(source = "image", target = "image")
    ExtendedAdDto AdtoExtendedAdDto(Ad ad);

    /**
      * List<Ad> -> List<AdDto>
      */

    List<AdDto> AdListToAdDtoList(List<Ad> ads);

    /**
     * Ad <--- CreateOrUpdateAdDto
     */

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ad CreateOrUpdateAdDtoToAdEntity(CreateOrUpdateAdDto createOrUpdateAdDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "image", ignore = true)
    void updateAdFromCreateOrUpdateAdDto(CreateOrUpdateAdDto createOrUpdateAdDto, @MappingTarget Ad ad);

    /**
     * AdDto + User -> Ad (ручной метод)
     */
    default Ad AdDtoToAdEntity(AdDto adDto, User user) {
        if (adDto == null) {
            return null;
        }

        Ad ad = new Ad();
        ad.setId(adDto.getPk()); // pk -> id
        ad.setTitle(adDto.getTitle());
        ad.setPrice(adDto.getPrice());
        ad.setUser(user);
        // description не маппится, т.к. его нет в AdDto

        return ad;
    }

    /**
     * ExtendedAdDto + User -> Ad (ручной метод)
     */
    default Ad ExtendedAdDtoToAdEntity(ExtendedAdDto extendedAdDto, User user) {
        if (extendedAdDto == null) {
            return null;
        }

        Ad ad = new Ad();
        ad.setId(extendedAdDto.getPk()); // pk -> id
        ad.setTitle(extendedAdDto.getTitle());
        ad.setPrice(extendedAdDto.getPrice());
        ad.setDescription(extendedAdDto.getDescription());
        ad.setUser(user);

        return ad;
    }
}
