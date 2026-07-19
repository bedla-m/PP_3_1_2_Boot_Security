package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/admin")
    public String adminPanel(ModelMap model) {
        List<User> userList = userService.getAllUser();
        List<Role> roleName = roleService.getAllRoles();
        model.addAttribute("users", userList);
        model.addAttribute("roles", roleName);
        model.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        Set<Role> roles = new HashSet<>(roleService.getRoleByIds(roleIds));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit")
    public String editUser(@RequestParam("id") Long id,
                           ModelMap model) {
        User user = userService.getUser(id);
        user.setPassword(null);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }
}