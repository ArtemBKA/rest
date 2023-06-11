package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    void save(Role role);

    List<Role> roleList();

    Optional<Role> getRoleById(long id);
}