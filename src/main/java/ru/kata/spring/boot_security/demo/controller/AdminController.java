package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.*;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String index(Model model, Authentication authentication) {
        model.addAttribute("user", authentication.getPrincipal());
        model.addAttribute("users", userService.userList());
        model.addAttribute("roles", roleService.roleList());
        return "admin";
    }

    @GetMapping("/user")
    public String showUserInfo(Model model, Authentication authentication) {
        model.addAttribute("user", authentication.getPrincipal());
        return "userForAdmin";
    }

    @GetMapping("/new")
    public String newUser(Model model, Authentication authentication) {
        model.addAttribute("user", authentication.getPrincipal());
        return "new";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("/save")
    public String addUser(@ModelAttribute("user") User user, @RequestParam String role) {
        user.setRoles(roleService.getRoles(role));
        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}/edit")
    public String editUser(@ModelAttribute("user") User user, @RequestParam(required = false) String role) {
        user.setRoles(roleService.getRoles(role));
        userService.update(user);
        return "redirect:/admin";
    }
}