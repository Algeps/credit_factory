package ru.sber.edu.credit_factory.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sber.edu.credit_factory.entity.User;
import ru.sber.edu.credit_factory.repo.UserRepository;

@Service
public class CF_UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User userInfo = userRepository.findByLogin(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User with login: " + username + " not found!");
        }
        return new CF_UserPrincipal(userInfo);
    }
}
