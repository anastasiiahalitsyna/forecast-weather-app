package com.project.weather.user.service;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Hasło nie spełnia wymagań bezpieczeństwa";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
