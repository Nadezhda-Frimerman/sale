package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdMapperTest {

    private final AdMapper adMapper = Mappers.getMapper(AdMapper.class);

    @Test
    void testAdToAdDto() {
        // Given
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .build();

        Ad ad = Ad.builder()
                .id(100)
                .title("Test Title")
                .price(1000)
                .description("Test Description")
                .user(user)
                .image("test.jpg")
                .build();

        // When
        AdDto adDto = adMapper.AdToAdDto(ad);

        // Then
        assertNotNull(adDto);
        assertEquals(100, adDto.getPk());
        assertEquals(1, adDto.getAuthor());
        assertEquals("Test Title", adDto.getTitle());
        assertEquals(1000, adDto.getPrice());
        assertEquals("test.jpg", adDto.getImage());
    }

    @Test
    void testAdToExtendedAdDto() {
        // Given
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("+123456789")
                .build();

        Ad ad = Ad.builder()
                .id(100)
                .title("Test Title")
                .price(1000)
                .description("Test Description")
                .user(user)
                .image("test.jpg")
                .build();

        // When
        ExtendedAdDto extendedAdDto = adMapper.AdtoExtendedAdDto(ad);

        // Then
        assertNotNull(extendedAdDto);
        assertEquals(100, extendedAdDto.getPk());
        assertEquals("Test Title", extendedAdDto.getTitle());
        assertEquals(1000, extendedAdDto.getPrice());
        assertEquals("Test Description", extendedAdDto.getDescription());
        assertEquals("test.jpg", extendedAdDto.getImage());
        assertEquals("John", extendedAdDto.getAuthorFirstName());
        assertEquals("Doe", extendedAdDto.getAuthorLastName());
        assertEquals("john@example.com", extendedAdDto.getEmail());
        assertEquals("+123456789", extendedAdDto.getPhone());
    }

    @Test
    void testCreateOrUpdateAdDtoToEntity() {
        // Given
        CreateOrUpdateAdDto dto = CreateOrUpdateAdDto.builder()
                .title("New Title")
                .price(2000)
                .description("New Description")
                .build();

        // When
        Ad ad = adMapper.CreateOrUpdateAdDtoToAdEntity(dto);

        // Then
        assertNotNull(ad);
        assertEquals("New Title", ad.getTitle());
        assertEquals(2000, ad.getPrice());
        assertEquals("New Description", ad.getDescription());
        assertNull(ad.getId());
        assertNull(ad.getUser());
        assertNull(ad.getImage());
        assertNull(ad.getComments());
    }

    @Test
    void testAdDtoToAdEntityWithUser() {
        // Given
        AdDto adDto = AdDto.builder()
                .pk(100)
                .title("Test Title")
                .price(1000)
                .image("test.jpg")
                .author(1)
                .build();

        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .build();

        // When
        Ad ad = adMapper.AdDtoToAdEntity(adDto, user);

        // Then
        assertNotNull(ad);
        assertEquals(100, ad.getId());
        assertEquals("Test Title", ad.getTitle());
        assertEquals(1000, ad.getPrice());
        assertEquals("test.jpg", ad.getImage());
        assertEquals(user, ad.getUser());
        assertNull(ad.getComments());
    }

    @Test
    void testUpdateEntityFromDto() {
        // Given
        Ad existingAd = Ad.builder()
                .id(100)
                .title("Old Title")
                .price(500)
                .description("Old Description")
                .image("old.jpg")
                .build();

        CreateOrUpdateAdDto updateDto = CreateOrUpdateAdDto.builder()
                .title("Updated Title")
                .price(1500)
                .description("Updated Description")
                .build();

        // When
        adMapper.updateAdFromCreateOrUpdateAdDto(updateDto, existingAd);

        // Then
        assertEquals("Updated Title", existingAd.getTitle());
        assertEquals(1500, existingAd.getPrice());
        assertEquals("Updated Description", existingAd.getDescription());
        // Эти поля не должны измениться
        assertEquals(100, existingAd.getId());
        assertEquals("old.jpg", existingAd.getImage());
    }

    @Test
    void testAdListToAdDtoList() {
        // Given
        User user = User.builder().id(1).build();

        Ad ad1 = Ad.builder().id(1).title("Title 1").price(1000).user(user).build();
        Ad ad2 = Ad.builder().id(2).title("Title 2").price(2000).user(user).build();

        List<Ad> ads = List.of(ad1, ad2);

        // When
        List<AdDto> adDtos = adMapper.AdListToAdDtoList(ads);

        // Then
        assertEquals(2, adDtos.size());
        assertEquals(1, adDtos.get(0).getPk());
        assertEquals(2, adDtos.get(1).getPk());
    }

    @Test
    void testNullSafety() {
        // Проверяем обработку null
        assertDoesNotThrow(() -> {
            adMapper.AdToAdDto(null);
            adMapper.CreateOrUpdateAdDtoToAdEntity(null);
            adMapper.AdListToAdDtoList(null);
        });
    }
}