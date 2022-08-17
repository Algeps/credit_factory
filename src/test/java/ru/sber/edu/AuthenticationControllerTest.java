package ru.sber.edu;


import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import ru.sber.edu.credit_factory.controllers.AuthenticationController;
import ru.sber.edu.credit_factory.entity.User;
import ru.sber.edu.credit_factory.enums.UserRole;
import ru.sber.edu.credit_factory.services.AuthenticationService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Тестируем контроллер AuthenticationController (модульное тестирование)
 */
@RunWith(SpringRunner.class)
public class AuthenticationControllerTest {

    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationService authenticationService;

    @Mock
    private Model model;

    private final User USER1 = User.builder().id(1L).userRole(UserRole.CLIENT).firstName("user1").lastName("user2")
            .dateOfBirth(LocalDate.now()).login("login").password("123").patronymic("ivanov")
            .build();

    @TestConfiguration
    static class Config {

        @Bean
        public AuthenticationController authenticationController(AuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
            return new AuthenticationController(authenticationService, passwordEncoder);
        }
    }


    @Test
    public void welcome_Test() {

        String actual = authenticationController.welcome();
        Assertions.assertEquals("welcome", actual);
    }


    @Test
    public void fromBanned_Test() {

        String actual = authenticationController.fromBanned();
        Assertions.assertEquals("banned", actual);
    }


    @Test
    public void registrationForm_Test() {

        String actual = authenticationController.registrationForm(model);

        verify(model, times(1)).addAttribute(eq("passMatch"), any());
        verify(model, times(1)).addAttribute(eq("userExist"), any());

        Assertions.assertEquals("registration", actual);
    }


    @Test
    public void registration_Success_Test() {

        String firstName = "user1";
        String lastName = "user1";
        String userName = "user1";
        String password1 = "user1";
        String password2 = "user1";
        String actual = "authenticationController.registration(firstName, lastName, userName, password1, password2)";

        verify(passwordEncoder, times(1)).encode(password1);
        verify(authenticationService, times(1)).createUser(any());

        Assertions.assertEquals("redirect:/login", actual);

    }


    @Test
    public void registration_Fail_LoginAlreadyExist_Test() {

        String firstName = "user1";
        String lastName = "user1";
        String userName = "user1";
        String password1 = "user1";
        String password2 = "user1";
        when(authenticationService.getUserByLogin(userName)).thenReturn(USER1);
        String actual = "authenticationController.registration(firstName, lastName, userName, password1, password2)";

        verify(authenticationService, times(1)).getUserByLogin(userName);

        Assertions.assertEquals("redirect:/registration", actual);

    }


    @Test
    public void registration_Fail_PasswordDontMatch_Test() {

        String firstName = "user1";
        String lastName = "user1";
        String userName = "user1";
        String password1 = "user1";
        String password2 = "user2";
        String actual = "authenticationController.registration(firstName, lastName, userName, password1, password2)";

        Assertions.assertEquals("redirect:/registration", actual);

    }

}
