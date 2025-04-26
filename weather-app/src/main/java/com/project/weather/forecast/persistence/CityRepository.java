package com.project.weather.forecast.persistence;

import com.project.weather.user.persistence.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findAllByAppUser(AppUser appUser);

    List<City> findAllByAppUserAndName(AppUser appUser, String name);

    @Modifying
    void deleteAllByAppUserAndName(AppUser appUser, String name);
}
