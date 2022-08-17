package ru.sber.edu.credit_factory.services;

import ru.sber.edu.credit_factory.edit_form.UserEditForm;
import ru.sber.edu.credit_factory.entity.User;
import ru.sber.edu.credit_factory.enums.UserRole;

import java.util.List;

public interface UserManagementService {

    List<User> getAllUsers();

    User getUserByLogin(String username);

    User getUserById(Long id);

    void updateUser(UserEditForm userEditForm);

    UserRole getRole(User user);
}
