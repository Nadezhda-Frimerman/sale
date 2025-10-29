package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Objects;

//@Data
public class Login {
    @Size(min = 8, max = 16)
    private String password = "";
    @Size(min = 4, max = 32)
    private String username = "";

    public Login() {
    }

    public Login(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return Objects.equals(password, login.password) && Objects.equals(username, login.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, username);
    }

    @Override
    public String toString() {
        return "Login{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
