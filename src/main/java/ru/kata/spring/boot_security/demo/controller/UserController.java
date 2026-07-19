package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String userPage(@RequestParam(value = "id", required = false) Long id, ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);

        if (id != null && userService.isAdmin(auth)) {
            user = userService.getUser(id);
        }
        model.addAttribute("user", user);
        return "user";
    }
}
