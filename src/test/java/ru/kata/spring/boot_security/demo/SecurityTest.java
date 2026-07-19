package ru.kata.spring.boot_security.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
public class SecurityTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    public void testPasswordEncoding() {
        String rawPassword = "admin";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assertThat(encodedPassword).isNotEqualTo(rawPassword);
        assertThat(encodedPassword).startsWith("$2a$");
        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
        assertThat(passwordEncoder.matches("falsepassword", encodedPassword)).isFalse();

        System.out.println(" Успешно зашифровано!" );
        System.out.println(" Открытый пароль: " + rawPassword);
        System.out.println(" Зашифрованный: " + encodedPassword);
        System.out.println(" matches(admin): " + passwordEncoder.matches(rawPassword, encodedPassword));
        System.out.println(" matches(wrong): " + passwordEncoder.matches("wrongpassword", encodedPassword));
        System.out.println();
    }
    @Test
    public void testUserExists() {
        UserDetails user = userDetailsService.loadUserByUsername("admin");

        assertThat(user).isNotNull();

        assertThat(user.getUsername()).isEqualTo("admin");

        assertThat(user.getPassword()).startsWith("$2a$");

        System.out.println(" Пользователь admin найден!");
        System.out.println(" Логин: " + user.getUsername());
        System.out.println(" Зашифрованный пароль в БД: " + user.getPassword());
        System.out.println();
    }

    @Test
    public void testLoginSuccess() {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken("admin", "admin");

        Authentication auth = authenticationManager.authenticate(authToken);

        assertThat(auth.isAuthenticated()).isTrue();
        assertThat(auth.getName()).isEqualTo("admin");

        System.out.println(" Логин admin/admin успешен!");
        System.out.println(" Пользователь: " + auth.getName());
        System.out.println(" Роли: " + auth.getAuthorities());
        System.out.println();
    }

    @Test
    public void testLoginFailure() {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken("admin", "wrongpassword");

        assertThatThrownBy(() -> authenticationManager.authenticate(authToken))
                .isInstanceOf(BadCredentialsException.class);

        System.out.println("Логин с неправильным паролем был отклонён");
    }
}
