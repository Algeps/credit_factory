package ru.sber.edu.credit_factory.services;

import ru.sber.edu.credit_factory.entity.User;

public interface AuthenticationService {
    void createUser(User client);
    User getUserByLogin(String username);
}
