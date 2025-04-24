package com.project.weather_app;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/sign-up")
    public String register(Model model) {
        AppUserDTO appUserDTO = new AppUserDTO();
        model.addAttribute(appUserDTO);
        model.addAttribute("success",false);
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String register(Model model,
                           @Valid @ModelAttribute AppUserDTO appUserDTO,
                           BindingResult result
    ) {
        if (appUserDTO.getConfirmPassword().isEmpty()) {
            result.addError(new FieldError("appUserDTO", "confirmPassword", "Wprowadź ponownie hasło"));
        } else if (!appUserDTO.getPassword().equals(appUserDTO.getConfirmPassword())) {
            result.addError(new FieldError("appUserDTO", "confirmPassword", "Hasła nie są takie same"));
        }

        AppUser user = userRepository.findByEmail(appUserDTO.getEmail());
        if (user != null) {
            result.addError(new FieldError("appUserDTO", "email", "Konto na podany adres email już istnieje"));
        }

        if (result.hasErrors()) {
            return "sign-up";
        }

        userService.createUser(appUserDTO);

        model.addAttribute("appUserDTO", new AppUserDTO());
        model.addAttribute("success",true);

        return "sign-up";
    }

    @GetMapping("/sign-in")
    public String login() {
        return "sign-in";
    }

    @GetMapping("/my-account")
    public String myAccount(Model model) {

        AppUser user = userService.findUser();

        model.addAttribute("user", user);
        model.addAttribute("success",false);
        model.addAttribute("pass", false);
        return "my-account";
    }

    @GetMapping("/edit-user")
    public String showEditUser(Model model) {
        AppUser user = userService.findUser();

        ProfilDTO profilDTO = new ProfilDTO();

        profilDTO.setFirstName(user.getFirstName());
        profilDTO.setLastName(user.getLastName());

        model.addAttribute("user", user);
        model.addAttribute("profilDTO", profilDTO);
        return "edit-user";
    }

    @PostMapping("/edit-user")
    public String editUser(Model model,
                           @Valid @ModelAttribute ProfilDTO profilDTO,
                           BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("profilDTO", profilDTO);
            return "edit-user";
        }

        AppUser user = userService.findUser();
        userService.editUser(profilDTO, user);

        model.addAttribute("user", user);
        model.addAttribute("success",true);
        return "my-account";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid @ModelAttribute ChangePasswordDTO changePasswordDTO,
                                 BindingResult result,
                                 Model model) {
        AppUser user = userService.findUser();

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            result.addError(new FieldError("changePasswordDTO", "confirmPassword", "Hasła nie są takie same"));
        }

        if (!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
            result.addError(new FieldError("changePasswordDTO", "currentPassword", "Nieprawidłowe hasło"));
        }

        if (result.hasErrors()) {
            return "change-password";
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);

        model.addAttribute("user", user);
        model.addAttribute("pass", true);

        return "my-account";
    }

    @DeleteMapping("/my-account/{id}")
    @Transactional
    public String deleteUser(@PathVariable Long id,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             RedirectAttributes redirectAttributes) {

        userService.deleteUser(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        Cookie jsessionCookie = new Cookie("JSESSIONID", null);
        jsessionCookie.setPath("/");
        jsessionCookie.setMaxAge(0);
        response.addCookie(jsessionCookie);

        Cookie rememberMeCookie = new Cookie("remember-me", null);
        rememberMeCookie.setPath("/");
        rememberMeCookie.setMaxAge(0);
        response.addCookie(rememberMeCookie);

        redirectAttributes.addFlashAttribute("message", true);
        return "redirect:/sign-in";
    }
}
