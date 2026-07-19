package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals("ROLE_ADMIN"));
    }
}
