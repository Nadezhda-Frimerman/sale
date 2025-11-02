package ru.skypro.homework.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Модель картинки")
public class Picture {
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
            format = "uri",
            description = "URL путь к файлу"
    )
    private String filePath;
    @Schema(
            type = "integer",
            format = "int64",
            description = "размер файла в байтах"
    )
    private long fileSize;
    @Schema(
            type = "string",
            description = "тип файла MIME"
    )
    private  String mediaType;
    @Lob
    @JsonIgnore
    @Schema(
            type = "string",
            format = "byte",
            description = "массив байтов - картинка, закодирована в base64"
    )
    private byte[] data;

}
