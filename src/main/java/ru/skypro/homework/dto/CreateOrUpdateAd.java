package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;

@Schema(description = "Пользователь")
public class CreateOrUpdateAd {
//    @Schema добавить в дто Tag, Operation
    @Size(min = 4, max = 32)
    private String title = "";
    @Min(value = 0)
    @Max(value = 10000000)
    private Integer price = 0;
    @Size(min = 8, max = 64)
    private String description = "";

    public CreateOrUpdateAd() {
    }

    public CreateOrUpdateAd(String title, Integer price, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrUpdateAd that = (CreateOrUpdateAd) o;
        return Objects.equals(title, that.title) && Objects.equals(price, that.price) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, description);
    }

    @Override
    public String toString() {
        return "CreateOrUpdateAd{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
