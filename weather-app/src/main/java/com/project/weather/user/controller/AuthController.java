package com.project.weather.user.controller;

import com.project.weather.security.JwtService;
import com.project.weather.user.service.UserService;
import com.project.weather.user.dto.AppUserRegistrationRequest;
import com.project.weather.user.dto.LoginRequest;
import com.project.weather.user.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        final var authToken = UsernamePasswordAuthenticationToken
                .unauthenticated(loginRequest.email(), loginRequest.password());
        authenticationManager.authenticate(authToken);
        final var token = jwtService.generateToken(loginRequest.email());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody AppUserRegistrationRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
