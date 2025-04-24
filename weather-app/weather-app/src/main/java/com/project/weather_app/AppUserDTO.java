package com.project.weather_app;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AppUserDTO {


    @NotBlank(message = "Email jest wymagany")
    @Pattern(regexp = "^[^@ ]+@[^@ ]+\\.[^@ \\.]{2,}$", message = "Niepoprawny format emaila")
    private String email;

    @StrongPassword
    private String password;

    @NotBlank(message = "Imię jest wymagane")
    @Size(max = 50, message = "Imię może miec maksymalnie 50 znaków")
    private String firstName;

    @NotBlank(message = "Nazwisko jest wymagane")
    @Size(max = 100, message = "Nazwisko może miec maksymalnie 100 znaków")
    private String lastName;

    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
