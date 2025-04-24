package com.project.weather_app;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfilDTO {

    @NotBlank(message = "Imię jest wymagane")
    @Size(max = 50, message = "Imię może miec maksymalnie 50 znaków")
    private String firstName;

    @NotBlank(message = "Nazwisko jest wymagane")
    @Size(max = 100, message = "Nazwisko może miec maksymalnie 100 znaków")
    private String lastName;

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

}
