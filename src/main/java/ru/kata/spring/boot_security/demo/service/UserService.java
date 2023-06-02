package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    void save(User user);

    void update(User user);

    void delete(long id);

    Optional<User> getUserById(long id);

    List<User> userList();
}