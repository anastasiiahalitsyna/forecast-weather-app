package com.project.weather.user.service;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        List<String> messages = new ArrayList<>();


        if (password.isEmpty()) {
            messages.add("Hasło jest wymagane");
        } else  {
            if (password.length() < 6) {
                messages.add("Hasło musi zawierać co najmniej 16 znaków");
            }
            if (password.length() > 255) {
                messages.add("Hasło może zawierać maksymalnie 255 znaków");
            }
            if (!password.matches(".*[a-z].*")) {
                messages.add("Hasło musi zawierać małą literę");
            }
            if (!password.matches(".*[A-Z].*")) {
                messages.add("Hasło musi zawierać wielką literę");
            }
            if (!password.matches(".*\\d.*")) {
                messages.add("Hasło musi zawierać cyfrę");
            }
            if (!password.matches(".*[*.!@#$%^&()\\[\\]{}\\[\"\\]:;'<>.,?/~`_+\\-=|].*")) {
                messages.add("Hasło musi zawierać znak specjalny");
            }
        }

        if (!messages.isEmpty()) {
            context.disableDefaultConstraintViolation();
            for (String message : messages) {
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            }
            return false;
        }

        return true;
    }
}
