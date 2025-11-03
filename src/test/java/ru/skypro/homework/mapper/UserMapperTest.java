package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testUserToUserDto() {
        // Given
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("+123456789")
                .image("avatar.jpg")
                .build();

        // When
        UserDto userDto = userMapper.UserToUserDto(user);

        // Then
        assertNotNull(userDto);
        assertEquals("John", userDto.getFirstName());
        assertEquals("Doe", userDto.getLastName());
        assertEquals("john@example.com", userDto.getEmail());
        assertEquals("+123456789", userDto.getPhone());
        assertEquals("avatar.jpg", userDto.getImage());
    }

    @Test
    void testUserDtoToUserEntity() {
        // Given
        UserDto userDto = UserDto.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("+987654321")
                .image("profile.jpg")
                .build();

        // When
        User user = userMapper.UserDtoToUserEntity(userDto);

        // Then
        assertNotNull(user);
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("+987654321", user.getPhone());
        assertEquals("profile.jpg", user.getImage());
        // Игнорируемые поля
        assertNull(user.getId());
        assertNull(user.getAds());
        assertNull(user.getComments());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    void testSafeUpdateUserDtoToUserEntity() {
        // Given
        User existingUser = User.builder()
                .id(1)
                .firstName("OldFirstName")
                .lastName("OldLastName")
                .phone("+111111111")
                .email("old@example.com")
                .image("old.jpg")
                .build();

        UpdateUserDto updateDto = new UpdateUserDto();
        updateDto.setFirstName("NewFirstName");
        updateDto.setLastName("NewLastName");
        updateDto.setPhone("+999999999");

        // When
        userMapper.safeUpdateUserDtoToUserEntity(updateDto, existingUser);

        // Then
        assertEquals("NewFirstName", existingUser.getFirstName());
        assertEquals("NewLastName", existingUser.getLastName());
        assertEquals("+999999999", existingUser.getPhone());
        // Поля, которые не должны измениться
        assertEquals(1, existingUser.getId());
        assertEquals("old@example.com", existingUser.getEmail());
        assertEquals("old.jpg", existingUser.getImage());
    }

    @Test
    void testSafeUpdateUserDtoToUserEntity_PartialUpdate() {
        // Given
        User existingUser = User.builder()
                .id(1)
                .firstName("OldFirstName")
                .lastName("OldLastName")
                .phone("+111111111")
                .build();

        UpdateUserDto updateDto = new UpdateUserDto();
        updateDto.setFirstName("NewFirstName");
        // lastName и phone не устанавливаем

        // When
        userMapper.safeUpdateUserDtoToUserEntity(updateDto, existingUser);

        // Then
        assertEquals("NewFirstName", existingUser.getFirstName());
        assertEquals("OldLastName", existingUser.getLastName()); // осталось старое значение
        assertEquals("+111111111", existingUser.getPhone()); // осталось старое значение
    }

    @Test
    void testSafeUpdateUserDtoToUserEntity_WithNullAndEmpty() {
        // Given
        User existingUser = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .phone("+123456789")
                .build();

        UpdateUserDto updateDto = new UpdateUserDto();
        updateDto.setFirstName(""); // пустая строка
        updateDto.setLastName(null); // null
        updateDto.setPhone("  "); // пробелы

        // When
        userMapper.safeUpdateUserDtoToUserEntity(updateDto, existingUser);

        // Then - поля не должны измениться
        assertEquals("John", existingUser.getFirstName());
        assertEquals("Doe", existingUser.getLastName());
        assertEquals("+123456789", existingUser.getPhone());
    }

    @Test
    void testRegisterDtoToUserEntity() {
        // Given
        RegisterDto registerDto = RegisterDto.builder()
                .username("newuser@example.com")
                .password("password123")
                .firstName("New")
                .lastName("User")
                .phone("+123456789")
                .role(Role.valueOf("USER"))
                .build();

        // When
        User user = userMapper.RegisterDtoToUserEntity(registerDto);

        // Then
        assertNotNull(user);
        assertEquals("newuser@example.com", user.getEmail()); // username -> email
        assertEquals("password123", user.getPassword());
        assertEquals("New", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals("+123456789", user.getPhone());
        assertEquals(Role.valueOf("USER"), user.getRole());
        // Игнорируемые поля
        assertNull(user.getId());
        assertNull(user.getAds());
        assertNull(user.getComments());
        assertNull(user.getImage());
    }

    @Test
    void testNullSafety() {
        // Проверяем обработку null
        assertDoesNotThrow(() -> {
            userMapper.UserToUserDto(null);
            userMapper.UserDtoToUserEntity(null);
            userMapper.RegisterDtoToUserEntity(null);

            // Для метода с @MappingTarget
            User user = new User();
            userMapper.safeUpdateUserDtoToUserEntity(null, user);
        });
    }

    @Test
    void testUserToUserDto_WithNullFields() {
        // Given
        User user = User.builder()
                .id(1)
                .firstName(null)
                .lastName(null)
                .email(null)
                .phone(null)
                .image(null)
                .build();

        // When
        UserDto userDto = userMapper.UserToUserDto(user);

        // Then
        assertNotNull(userDto);
        assertNull(userDto.getFirstName());
        assertNull(userDto.getLastName());
        assertNull(userDto.getEmail());
        assertNull(userDto.getPhone());
        assertNull(userDto.getImage());
    }
}