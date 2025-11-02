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
@Schema(description = "Модель комментария")
public class Comment {
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
            description = "текст комментария"
    )
    @Size(min = 8, max = 64)
    private String content;
}
