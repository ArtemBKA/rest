package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.*;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping()
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("user", authentication.getPrincipal());
        model.addAttribute("users", userService.userList());
        return "admin";
    }

    @GetMapping("/admin/user")
    public String showUserInfo(Model model, Authentication authentication) {
        model.addAttribute("user", authentication.getPrincipal());
        return "userForAdmin";
    }

    @GetMapping("/new")
    public String newUser(Model model, Authentication authentication
    ) {
        model.addAttribute("user", authentication.getPrincipal());
        return "new";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    private Set<Role> getRoles(String roleAdmin) {
        Set<Role> roles = new HashSet<>();
        if (roleAdmin.equals("ADMIN") || roleAdmin.matches(".*\\bADMIN\\b.*")) {
            roles.add(roleService.getRoleByName("ADMIN"));
        }
        if (roleAdmin.equals("USER") || roleAdmin.matches(".*\\bUSER\\b.*")) {
            roles.add(roleService.getRoleByName("USER"));
        }
        return roles;
    }

    @PostMapping("/save")
    public String addUser(@ModelAttribute("user") User user, @RequestParam String roleAdmin) {
        user.setRoles(getRoles(roleAdmin));
        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}/edit")
    public String editUser(@ModelAttribute("user") User user, @RequestParam(required = false) String roleAdmin) {
        user.setRoles(getRoles(roleAdmin));
        userService.update(user);
        return "redirect:/admin";
    }
}