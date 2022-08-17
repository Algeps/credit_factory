package ru.sber.edu.credit_factory.controllers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sber.edu.credit_factory.services.AuthenticationService;

class AuthenticationControllerTest {
    @TestConfiguration
    static class Config {

        @Bean
        public AuthenticationController authenticationController(AuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
            return new AuthenticationController(authenticationService, passwordEncoder);
        }
    }
}