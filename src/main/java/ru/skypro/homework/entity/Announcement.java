package ru.skypro.homework.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Модель данных объявления")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            type = "integer",
            format = "int32",
            description = "id объявления"
    )
    private Integer id;
    @Schema(
            type = "string",
            description = "заголовок объявления"
    )
    @Size(min = 4, max = 32)
    private String title;
    @Schema(
            type = "integer",
            format = "int32",
            description = "цена объявления"
    )
    private Integer price;

    @Schema(
            type = "string",
            description = "описание объявления"
    )
    @Size(min = 8, max = 64)
    private String description;

    @Schema(
            type = "integer",
            format = "int32",
            description = "id пользователя"
    )
    private Integer userId;
    @Schema(
            type = "string",
            description = "ссылка на картинку объявления"
    )
    private String image;

}
