package ru.skypro.homework.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Модель данных пользователя")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    @Size(min = 8, max = 16)
    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    @Enumerated
    private Role role;

    private String image;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Ad> ads;
}
