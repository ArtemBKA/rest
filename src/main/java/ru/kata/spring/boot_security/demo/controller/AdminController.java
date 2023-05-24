package ru.kata.spring.boot_security.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.*;
import org.springframework.ui.ModelMap;
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
    public String index(Model model) {
        model.addAttribute("users", userService.userList());
        return "admin/users";
    }

    @GetMapping("/search")
    public String searchUserById(ModelMap model, @RequestParam(value = "keyword") long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/search";
    }

    @GetMapping("/new")
    public String newUser(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("roles", roleService.roleList());
        return "admin/new";
    }

    @GetMapping("/edit")
    public String newUser(Model model, @RequestParam("id") long id) {
        model.addAttribute("roles", roleService.roleList());
        model.addAttribute("user", userService.getUserById(id));
        return "admin/edit";
    }

//    @PostMapping
//    public String create(@ModelAttribute("user") User user) {
//        userService.save(user);
//        return "redirect:/admin";
//    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping
    public String delete(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}


//@Controller
//public class AdminController {
//    private final UserService userService;
//
//    public AdminController(UserService userService) {
//        this.userService = userService;
//    }

//    @GetMapping("/")
//    public String printUsers(ModelMap model) {
//        model.addAttribute("userList", userService.userList());
//        return "index";
//    }
//
//    @GetMapping("/new")
//    public String newUser(ModelMap model) {
//        model.addAttribute("user", new User());
//        return "new";
//    }
//
//    @PostMapping("/save")
//    public String saveUser(@ModelAttribute("user") User user) {
//        userService.save(user);
//        return "redirect:/";
//    }
//
//    @GetMapping("/search")
//    public String searchUserById(ModelMap model, @RequestParam(value = "keyword") long id) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "search";
//    }
//
//    @GetMapping("/edit")
//    public String editUser(ModelMap model, @RequestParam long id) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String updateUser(@ModelAttribute("user") User user) {
//        userService.update(user);
//        return "redirect:/";
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable("id") long id) {
//        userService.delete(id);
//        return "redirect:/";
//    }
//
//    @GetMapping("/user")
//    public String user() {
//        return "user";
//    }
//
//    @GetMapping("/index")
//    public String index() {
//        return "index";
//    }
//}