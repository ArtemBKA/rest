package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/")
    public String rootBoot() {
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String readGet(Model model, Authentication authentication) {
        model.addAttribute("user", authentication.getPrincipal());
        return "users";
    }
}