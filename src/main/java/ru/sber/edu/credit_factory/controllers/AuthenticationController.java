package ru.sber.edu.credit_factory.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sber.edu.credit_factory.entity.User;
import ru.sber.edu.credit_factory.services.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
@Slf4j
public class AuthenticationController {
    private boolean passMatch = true;
    private boolean userExist = false;

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = {"/", "/home"})
    public String welcome() {
        passMatch = true;
        userExist = false;

        return "home";
    }

    /**
     * @param model входящее значение.
     * @return страницу для регестрации.
     */
    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("passMatch", passMatch);
        model.addAttribute("userExist", userExist);
        log.info("GET '/registration'");

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName,
                               @RequestParam("surName") String surName,
                               @RequestParam("dateOfBirth") String dateOfBirth,
                               @RequestParam("userName") String userName,
                               @RequestParam("password1") String password1,
                               @RequestParam("password2") String password2,
                               HttpServletRequest request) {
        String logOut = request.getMethod() + " '" + request.getServletPath() + "'";

        if (authenticationService.getUserByLogin(userName) != null) {
            userExist = true;
            passMatch = true;
            log.error(logOut + " - login already exists");

            return "redirect:/registration";
        } else if (!password1.equals(password2)) {
            passMatch = false;
            userExist = false;
            log.error(logOut + " - password1 != password2");

            return "redirect:/registration";
        } else {
            String hashedPassword = passwordEncoder.encode(password1);
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPatronymic(surName);
            user.setDateOfBirth(LocalDate.parse(dateOfBirth));
            user.setLogin(userName);
            user.setPassword(hashedPassword);
            authenticationService.createUser(user);
            log.info(logOut + " - successful registration");

            return "redirect:/login";
        }
    }

    /**
     *
     * @return страницу забанненого клиента.
     */
    @GetMapping("/banned")
    public String fromBanned() {
        return "banned";
    }
}
