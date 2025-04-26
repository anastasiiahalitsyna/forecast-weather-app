package com.project.weather.user.service;

import com.project.weather.user.dto.AppUserProfile;
import com.project.weather.user.dto.AppUserRegistrationRequest;
import com.project.weather.user.persistence.AppUser;
import com.project.weather.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(AppUserRegistrationRequest appUserRegistrationRequest) {
        AppUser newUser = new AppUser();
        newUser.setFirstName(appUserRegistrationRequest.getFirstName());
        newUser.setLastName(appUserRegistrationRequest.getLastName());
        newUser.setEmail(appUserRegistrationRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(appUserRegistrationRequest.getPassword()));
        newUser.setCreatedAt(LocalDate.now());
        userRepository.save(newUser);
    }

    public void editUser(AppUserProfile appUserProfile, AppUser user) {
        user.setFirstName(appUserProfile.getFirstName());
        user.setLastName(appUserProfile.getLastName());
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
