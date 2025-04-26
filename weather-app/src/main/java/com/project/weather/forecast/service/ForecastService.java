package com.project.weather.forecast.service;

import com.project.weather.forecast.dto.CityExistsResponse;
import com.project.weather.forecast.dto.CityResponse;
import com.project.weather.forecast.persistence.City;
import com.project.weather.forecast.persistence.CityRepository;
import com.project.weather.user.persistence.AppUser;
import com.project.weather.user.persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForecastService {

    private final CityRepository cityRepository;
    private final UserRepository userRepository;


    @Transactional
    public void saveCity(String cityName, String email) {
        AppUser byEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));
        City city = new City();
        city.setName(cityName);
        city.setAppUser(byEmail);
        cityRepository.save(city);
    }

    public CityExistsResponse exists(String cityName, String email) {
        AppUser byEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));
        boolean exists = !cityRepository.findAllByAppUserAndName(byEmail, cityName).isEmpty();
        return new CityExistsResponse(exists);
    }

    public Set<CityResponse> getCities(String email) {
        AppUser byEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));

        return cityRepository.findAllByAppUser(byEmail)
                .stream()
                .map(city -> new CityResponse(city.getId(), city.getName()))
                .collect(Collectors.toSet());
    }

    @Transactional
    public void delete(String cityName, String email) {
        AppUser byEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));
        cityRepository.deleteAllByAppUserAndName(byEmail, cityName);
    }
}
