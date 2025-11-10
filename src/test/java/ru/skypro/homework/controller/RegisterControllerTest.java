package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegisterController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthServiceImpl authServiceImpl; // мок сервиса

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Негативный тест: регистрация с невалидным логином")
    void register_withInvalidUserName_returnsBadRequest() throws Exception {
        // Формируем объект с некорректным логином
        RegisterDto invalidRegister = RegisterDto.builder()
                .username("ab") // меньше 4 символов
                .password("12345678") // valid
                .firstName("Ann") // valid
                .lastName("Bloom") // valid
                .phone("+7 222 333-4444") // valid
                .role(Role.USER)
                .build();

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegister)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Негативный тест: регистрация с невалидным паролем")
    void register_withInvalidPassword_returnsBadRequest() throws Exception {
        // Формируем объект с некорректным паролем
        RegisterDto invalidRegister = RegisterDto.builder()
                .username("Bloom") // valid
                .password("123456") // меньше 8 символов
                .firstName("Ann") // valid
                .lastName("Bloom") // valid
                .phone("+7 222 333-4444") // valid
                .role(Role.USER)
                .build();

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegister)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Негативный тест: регистрация с невалидным именем")
    void register_withInvalidFirstName_returnsBadRequest() throws Exception {
        // Формируем объект с некорректным именем
        RegisterDto invalidRegister = RegisterDto.builder()
                .username("Bloom") // valid
                .password("12345678") // valid
                .firstName("A") // меньше 2 символов
                .lastName("Bloom") // valid
                .phone("+7 222 333-4444") // valid
                .role(Role.USER)
                .build();

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegister)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Негативный тест: регистрация с невалидной фамилией")
    void register_withInvalidLastName_returnsBadRequest() throws Exception {
        // Формируем объект с некорректной фамилией
        RegisterDto invalidRegister = RegisterDto.builder()
                .username("Bloom") // valid
                .password("12345678") // valid
                .firstName("Ann") // valid
                .lastName("B") // меньше 2 символов
                .phone("+7 222 333-4444") // valid
                .role(Role.USER)
                .build();

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegister)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Негативный тест: регистрация с невалидным телефоном")
    void register_withInvalidPhone_returnsBadRequest() throws Exception {
        // Формируем объект с некорректным телефоном
        RegisterDto invalidRegister = RegisterDto.builder()
                .username("Bloom") // valid
                .password("12345678") // valid
                .firstName("Ann") // valid
                .lastName("Bloom") // valid
                .phone("invalid_phone") // не соответствует шаблону
                .role(Role.USER)
                .build();

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegister)))
                .andExpect(status().isBadRequest());
    }
}