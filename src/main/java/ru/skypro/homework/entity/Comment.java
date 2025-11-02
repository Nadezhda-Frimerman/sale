package ru.skypro.homework.entity;
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
@Schema(description = "Модель комментария")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

    @Size(min = 8, max = 64)
    private String content;
    @ManyToOne
    @JoinColumn(name="ad_id")
    private Ad ad;
}
