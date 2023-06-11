package ru.kata.spring.boot_security.demo.configs;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitDB {
    private final UserService userService;
    private final RoleService roleService;

    public InitDB(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createUsersWithRoles() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        roleService.save(role1);
        roleService.save(role2);
        Set<Role> set1 = new HashSet<>();
        set1.add(role1);
        Set<Role> set2 = new HashSet<>();
        set2.add(role2);
        Set<Role> set3 = new HashSet<>();
        set3.add(role1);
        set3.add(role2);
        User user1 = new User("Поль", "Зователь", 21, "user@mail.ru", "user", set2);
        User user2 = new User("Ад", "Мин", 22, "admin@mail.ru", "admin", set1);
        User user3 = new User("Юзер", "Админ", 23, "useradmin@mail.ru", "useradmin", set3);
        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
    }
}