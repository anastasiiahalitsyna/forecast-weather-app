package com.project.weather.user.dto;

import com.project.weather.user.service.StrongPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    private String currentPassword;
    @StrongPassword
    private String newPassword;
    private String confirmPassword;
}
