package ru.skypro.homework.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Пользователь")
public class CreateOrUpdateAd {
//    @Schema добавить в дто Tag, Operation
    @Size(min = 4, max = 32)
    private String title;
    @Min(value = 0)
    @Max(value = 10000000)
    private Integer price;
    @Size(min = 8, max = 64)
    private String description;


}
