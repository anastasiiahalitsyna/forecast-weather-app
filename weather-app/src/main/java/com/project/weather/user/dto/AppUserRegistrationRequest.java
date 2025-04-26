package com.project.weather.user.dto;

import com.project.weather.user.service.StrongPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRegistrationRequest {

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

}
