package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/")
public class AdminController {
    @GetMapping("")
    public String rootBoot(){
        return "redirect:/login";
    }
}