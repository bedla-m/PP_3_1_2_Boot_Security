package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.RoleName;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DataInit {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostConstruct
    public void testStart() {
        //Создали роли
        Role role1 = new Role(RoleName.ROLE_ADMIN);
        Role role2 = new Role(RoleName.ROLE_USER);

        //Сохранили в БД
        roleService.saveRole(role1);
        roleService.saveRole(role2);

        //Поиск ролей из БД
        Role roleUser = roleService.findByRoleName(RoleName.ROLE_USER);
        Role roleAdmin = roleService.findByRoleName(RoleName.ROLE_ADMIN);

        //Создание юзеров
        User user1 = new User("Debbie", "Harry", (byte) 32);
        User user2 = new User("Courtney", "Love", (byte) 25);

        user1.setUsername("user");
        user1.setPassword(passwordEncoder.encode("user"));

        user2.setUsername("admin");
        user2.setPassword(passwordEncoder.encode("admin"));

        //Привязка найденных ролей к юзеру
        user1.setRoles(Set.of(roleUser));
        user2.setRoles(Set.of(roleAdmin));

        userService.saveUser(user1);
        userService.saveUser(user2);
    }
}
