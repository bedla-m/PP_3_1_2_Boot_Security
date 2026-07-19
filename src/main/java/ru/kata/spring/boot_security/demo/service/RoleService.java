package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.RoleName;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    List<Role> getRoleByIds(List<Long> ids);

    Role findByRoleName(RoleName roleName);

    void saveRole(Role role);
}
