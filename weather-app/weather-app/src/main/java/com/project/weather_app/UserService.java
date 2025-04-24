package com.project.weather_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(AppUserDTO appUserDTO) {
        AppUser newUser = new AppUser();
        newUser.setFirstName(appUserDTO.getFirstName());
        newUser.setLastName(appUserDTO.getLastName());
        newUser.setEmail(appUserDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        newUser.setCreatedAt(LocalDate.now());

        userRepository.save(newUser);
    }

    public void editUser(ProfilDTO profilDTO, AppUser user) {
        user.setFirstName(profilDTO.getFirstName());
        user.setLastName(profilDTO.getLastName());

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public AppUser findUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return userRepository.findByEmail(userDetails.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Użytkownik o adresie " + email + " nie istnieje!");
        }

        var springUser = User.withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
        return springUser;
    }
}
