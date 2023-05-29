package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    void save(Role role);

    List<Role> roleList();

    Role getRoleById(long id);

    Role getRoleByName(String name);

    Set<Role> getRoles(String role);
}