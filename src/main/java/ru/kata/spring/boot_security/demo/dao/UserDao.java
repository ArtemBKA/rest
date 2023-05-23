package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void save(User user);

    void update(User user);

    void delete(long id);

    User getUserById(long id);

    List<User> userList();
}