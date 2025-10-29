package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CreateOrUpdateComment {
//    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 8, max = 64)
    private String text = "";

    public CreateOrUpdateComment() {
    }

    public CreateOrUpdateComment(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrUpdateComment that = (CreateOrUpdateComment) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    @Override
    public String toString() {
        return "CreateOrUpdateComment{" +
                "text='" + text + '\'' +
                '}';
    }
}
