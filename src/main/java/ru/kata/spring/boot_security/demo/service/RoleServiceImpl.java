package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.RoleName;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getRoleByIds(List<Long> ids) {
        return roleRepository.findAllById(ids);
    }

    public Role findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role: " + roleName.name() + " not found!"));
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
