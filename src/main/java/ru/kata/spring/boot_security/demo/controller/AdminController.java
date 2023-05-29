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

    @PostMapping("/save")
    public String addUser(@ModelAttribute("user") User user, @RequestParam String roleAdmin) {
        Set<Role> roles = new HashSet<>();
        if (roleAdmin.equals("ADMIN")) {
            roles.add(roleService.getRoleByName("ADMIN"));
        } else {
            roles.add(roleService.getRoleByName("USER"));
        }
        if (roleAdmin.matches(".*\\bUSER\\b.*") && roleAdmin.matches(".*\\bADMIN\\b.*")) {
            roles.add(roleService.getRoleByName("ADMIN"));
            roles.add(roleService.getRoleByName("USER"));
        }
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}/edit")
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam(required = false) String roleAdmin,
                           @PathVariable("id") long id) {
        Set<Role> roles = new HashSet<>();
        if (roleAdmin.equals("ADMIN")) {
            roles.add(roleService.getRoleByName("ADMIN"));
        } else {
            roles.add(roleService.getRoleByName("USER"));
        }
        if (roleAdmin.matches(".*\\bUSER\\b.*") && roleAdmin.matches(".*\\bADMIN\\b.*")) {
            roles.add(roleService.getRoleByName("ADMIN"));
            roles.add(roleService.getRoleByName("USER"));
        }
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin";
    }
}