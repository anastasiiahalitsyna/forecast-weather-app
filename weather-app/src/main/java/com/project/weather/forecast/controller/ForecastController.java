package com.project.weather.forecast.controller;

import com.project.weather.forecast.dto.CityExistsResponse;
import com.project.weather.forecast.dto.CityResponse;
import com.project.weather.forecast.service.ForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/forecasts")
public class ForecastController {

    private final ForecastService forecastService;

    @PostMapping("/{cityName}")
    public ResponseEntity<Void> saveCity(@PathVariable String cityName, Principal principal) {
        forecastService.saveCity(cityName, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<CityExistsResponse> exists(@PathVariable String cityName, Principal principal) {
        return ResponseEntity.ok(forecastService.exists(cityName, principal.getName()));
    }

    @DeleteMapping("/{cityName}")
    public ResponseEntity<Void> delete(@PathVariable String cityName, Principal principal) {
        forecastService.delete(cityName, principal.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Set<CityResponse>> getCities(Principal principal) {
        Set<CityResponse> cities = forecastService.getCities(principal.getName());
        return ResponseEntity.ok(cities);
    }
}
