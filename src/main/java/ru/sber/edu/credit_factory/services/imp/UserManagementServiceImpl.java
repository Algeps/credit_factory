package ru.sber.edu.credit_factory.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sber.edu.credit_factory.edit_form.UserEditForm;
import ru.sber.edu.credit_factory.entity.User;
import ru.sber.edu.credit_factory.enums.UserRole;
import ru.sber.edu.credit_factory.repo.UserRepository;
import ru.sber.edu.credit_factory.services.UserManagementService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManagementServiceImpl implements UserManagementService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserManagementServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Все пользователи из БД.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
    }

    /**
     * Метод UserManagmentServiceImpl#getUserByLogin(String username) ищет пользователя по логину
     *
     * @param username логин искомого пользователя
     * @return возвращает нужного нам пользователя или NULL если пользователь не найден
     */
    @Override
    public User getUserByLogin(String username) {
        return userRepository.findByLogin(username);
    }

    /**
     * Метод ищет пользователя по id.
     */
    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    /**
     * Обновление данных пользователя.
     */
    @Override
    public void updateUser(UserEditForm userEditForm) {
        User user = userRepository.getById(userEditForm.getId());
        user.setLogin(userEditForm.getLogin());
        user.setFirstName(userEditForm.getFirstName());
        user.setLastName(userEditForm.getLastName());
        user.setPatronymic(userEditForm.getPatronymic());
        user.setUserRole(userEditForm.getUserRole());
        if (userEditForm.getChangePassword() != null && userEditForm.getChangePassword()) {
            final String hashedPassword = passwordEncoder.encode(userEditForm.getNewPassword());
            user.setPassword(hashedPassword);
        }
        userRepository.save(user);
    }

    /**
     * Запрос на проверку роли.
     */
    @Override
    public UserRole getRole(User user) {
        return userRepository.findByLogin(user.getLogin()).getUserRole();
    }
}
