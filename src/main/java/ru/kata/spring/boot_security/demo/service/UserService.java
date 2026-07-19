package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.Authentication;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {
    void saveUser(User user);

    void deleteUser(Long id);

    User getUser(Long id);

    User findByUsername(String username);

    List<User> getAllUser();

    boolean isAdmin(Authentication auth);

}
