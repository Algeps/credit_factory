package ru.sber.edu.credit_factory.services.imp;

import org.springframework.stereotype.Service;
import ru.sber.edu.credit_factory.entity.User;
import ru.sber.edu.credit_factory.repo.UserRepository;
import ru.sber.edu.credit_factory.services.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByLogin(String username) {
        return userRepository.findByLogin(username);
    }
}
